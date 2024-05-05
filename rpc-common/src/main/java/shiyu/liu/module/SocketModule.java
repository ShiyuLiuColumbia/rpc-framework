package shiyu.liu.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import shiyu.liu.HelloService;
import shiyu.liu.proxy.RpcClientProxyInvocationHandler;
import shiyu.liu.socket.client.SocketClientV1;
import shiyu.liu.socket.client.SocketClientV2;
import shiyu.liu.socket.handler.SocketRequestHandlerV1;
import shiyu.liu.socket.server.SocketServerV1;

import java.lang.reflect.InvocationHandler;

public class SocketModule extends AbstractModule {
    @Provides
    @Singleton
    public SocketClientV1 providerSocketClientV1() {
        return new SocketClientV1();
    }

    @Provides
    @Singleton
    public SocketClientV2 providerSocketClientV2() {
        final InvocationHandler invocationHandler = new RpcClientProxyInvocationHandler();
        return new SocketClientV2(invocationHandler);
    }

    @Provides
    @Singleton
    public SocketServerV1 providerSocketServerV1(final HelloService helloService,
                                                 final SocketRequestHandlerV1 requestHandler) {
        return new SocketServerV1(helloService, requestHandler);
    }

    @Provides
    @Singleton
    public SocketRequestHandlerV1 provideSocketRequestHandlerV1() {
        return new SocketRequestHandlerV1();
    }
}
