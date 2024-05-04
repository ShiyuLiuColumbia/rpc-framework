package shiyu.liu.socket.client;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.Hello;
import shiyu.liu.HelloService;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
@NoArgsConstructor
public class SocketClientV1 {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8080;
    public void start() {
        try {
            final Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            log.info("Server connected");
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            final Method method = HelloService.class.getMethod("Hello", Hello.class);
            final Hello data = new Hello();
            data.setMessage("liushiyu");
            final RpcRequest rpcRequest = RpcRequest
                    .builder()
                    .methodName(method.getName())
                    .parameters(new Object[]{data})
                    .paramTypes(method.getParameterTypes())
                    .build();
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            final RpcResponse response = (RpcResponse)objectInputStream.readObject();
            log.info("Receive response: {}", response.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("Caught java.lang.ClassNotFoundException");
            throw new RuntimeException("Caught java.lang.ClassNotFoundException", e);
        } catch (NoSuchMethodException e) {
            log.error("Caught NoSuchMethodException");
            throw new RuntimeException("Caught NoSuchMethodException", e);
        } catch (UnknownHostException e) {
            log.error("Caught UnknownHostException");
            throw new RuntimeException("Caught UnknownHostException", e);
        } catch (IOException e) {
            log.error("Caught IOException");
            throw new RuntimeException("Caught IOException", e);
        }
    }
}
