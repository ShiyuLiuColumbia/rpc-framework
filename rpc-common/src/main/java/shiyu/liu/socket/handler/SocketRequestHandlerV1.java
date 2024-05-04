package shiyu.liu.socket.handler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.HelloService;
import shiyu.liu.model.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@NoArgsConstructor
public class SocketRequestHandlerV1 {
    public Object handle(final HelloService service, final RpcRequest request) {
        Object result;
        try {
            final Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(service, request.getParameters());
            log.info("Service successful invoke method: {}",  request.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;

    }
}
