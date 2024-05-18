package shiyu.liu.socket.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.HelloService;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;
import shiyu.liu.serialization.KryoSerializerV1;
import shiyu.liu.socket.handler.SocketRequestHandlerV1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@RequiredArgsConstructor
public class SocketServerV2 {
    private static final int SERVER_PORT = 8080;
    private final HelloService helloService;
    private final SocketRequestHandlerV1 requestHandler;
    private final KryoSerializerV1 kryoSerializerV1;
    public void start() {
        try {
            final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            log.info("Waiting for client to connect");
            final Socket clientSocket = serverSocket.accept();
            log.info("Client has connected");
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
