package shiyu.liu.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;
import shiyu.liu.serialization.KryoSerializerV1;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
@RequiredArgsConstructor
public class RpcClientProxyInvocationHandlerV2 implements InvocationHandler {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8080;
    private final KryoSerializerV1 kryoSerializerV1;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            final Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            log.info("Server connected");
            final OutputStream outputStream = socket.getOutputStream();
            final InputStream inputStream = socket.getInputStream();
            final RpcRequest rpcRequest = RpcRequest
                    .builder()
                    .methodName(method.getName())
                    .parameters(args)
                    .paramTypes(method.getParameterTypes())
                    .build();
            byte[] rpcRequestInBytes = kryoSerializerV1.serialize(rpcRequest);
            //TODO: this can only supports byte array length smaller than 128
            outputStream.write(rpcRequestInBytes.length);
            outputStream.write(rpcRequestInBytes);
            outputStream.flush();
            log.info("RpcRequest has been successfully sent");
            int length = inputStream.read();
            byte[] resultBuff = new byte[length];
            inputStream.read(resultBuff);
            final RpcResponse response = kryoSerializerV1.deserialize(resultBuff, RpcResponse.class);
            log.info("Receive response: {}", response.getMessage());
            return response.getMessage();
        } catch (UnknownHostException e) {
            log.error("Caught UnknownHostException");
            throw new RuntimeException("Caught UnknownHostException", e);
        } catch (IOException e) {
            log.error("Caught IOException");
            throw new RuntimeException("Caught IOException", e);
        }
    }
}
