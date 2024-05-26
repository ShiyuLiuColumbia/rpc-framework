package shiyu.liu.socket.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.HelloService;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;
import shiyu.liu.serialization.KryoSerializerV1;
import shiyu.liu.socket.handler.SocketRequestHandlerV1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class SocketServerV3 {
    private static final int SERVER_PORT = 8080;
    private final HelloService helloService;
    private final SocketRequestHandlerV1 requestHandler;
    private final KryoSerializerV1 kryoSerializerV1;
    public void start() {
        final Executor executor = Executors.newFixedThreadPool(10);
        try {
            final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                log.info("Waiting for client to connect");
                final Socket clientSocket = serverSocket.accept();
                log.info("Client has connected");
                executor.execute(new ServerRunnable(clientSocket));
            }
        } catch (IOException e) {
            log.error("Caught IOException");
            throw new RuntimeException("Caught IOException", e);
        }
    }

    private class ServerRunnable implements Runnable {
        private final Socket clientSocket;

        public ServerRunnable(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            log.info("Current thread id: {}", Thread.currentThread().getId());
            try{
                final InputStream inputStream = clientSocket.getInputStream();
                final OutputStream outputStream = clientSocket.getOutputStream();

                int length = inputStream.read();
                byte[] resultBuff = new byte[length];
                inputStream.read(resultBuff);
                final RpcRequest rpcRequest = kryoSerializerV1.deserialize(resultBuff, RpcRequest.class);
                log.info("Receive RpcRequest: {}", rpcRequest);
                String result = (String)requestHandler.handle(helloService, rpcRequest);
                final RpcResponse rpcResponse = RpcResponse
                        .builder()
                        .message(result)
                        .build();
                final byte[] rpcResponseInBytes = kryoSerializerV1.serialize(rpcResponse);
                outputStream.write(rpcResponseInBytes.length);
                outputStream.write(rpcResponseInBytes);
                outputStream.flush();
            } catch (IOException e) {
                log.error("Caught IOException");
                throw new RuntimeException("Caught IOException", e);
            }
        }
    }
}
