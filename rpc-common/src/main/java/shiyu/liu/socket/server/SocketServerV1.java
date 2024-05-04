package shiyu.liu.socket.server;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.HelloService;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;
import shiyu.liu.serviceimpl.HelloServiceImpl;
import shiyu.liu.socket.handler.SocketRequestHandlerV1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@RequiredArgsConstructor
public class SocketServerV1 {
    private static final int SERVER_PORT = 8080;
    private final HelloService helloService;
    private final SocketRequestHandlerV1 requestHandler;
    public void start() {
        try {
            final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            log.info("Waiting for client to connect");
            final Socket clientSocket = serverSocket.accept();
            log.info("Client has connected");
            final ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true) {
                final RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
                log.info("Receive RpcRequest: {}", rpcRequest);
                String result = (String)requestHandler.handle(helloService, rpcRequest);
                final RpcResponse rpcResponse = RpcResponse
                        .builder()
                        .message(result)
                        .build();
                objectOutputStream.writeObject(rpcResponse);
                objectOutputStream.flush();
            }
        } catch (ClassNotFoundException e) {
            log.error("Caught ClassNotFoundException");
            throw new RuntimeException("Caught ClassNotFoundException", e);
        } catch (IOException e) {
            log.error("Caught IOException");
            throw new RuntimeException("Caught IOException", e);
        }
    }
}
