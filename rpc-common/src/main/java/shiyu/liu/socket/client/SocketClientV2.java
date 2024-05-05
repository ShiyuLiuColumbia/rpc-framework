package shiyu.liu.socket.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.Hello;
import shiyu.liu.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Slf4j
@RequiredArgsConstructor
public class SocketClientV2 {
    private final InvocationHandler invocationHandler;

    public void start() {
        final Hello hello = new Hello();
        hello.setMessage("SocketClientV2WithDynamicProxy");
        final HelloService helloService = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class<?>[]{HelloService.class},
                invocationHandler);
        final String response = helloService.hello(hello);
        log.info("Got response: {}", response);
    }
}
