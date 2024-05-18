package shiyu.liu.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import shiyu.liu.HelloService;
import shiyu.liu.proxy.RpcClientProxyInvocationHandlerV1;
import shiyu.liu.proxy.RpcClientProxyInvocationHandlerV2;
import shiyu.liu.serialization.KryoSerializerV1;
import shiyu.liu.socket.client.SocketClientV1;
import shiyu.liu.socket.client.SocketClientV2;
import shiyu.liu.socket.client.SocketClientV3;
import shiyu.liu.socket.handler.SocketRequestHandlerV1;
import shiyu.liu.socket.server.SocketServerV1;
import shiyu.liu.socket.server.SocketServerV2;

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
        final InvocationHandler invocationHandler = new RpcClientProxyInvocationHandlerV1();
        return new SocketClientV2(invocationHandler);
    }

    @Provides
    @Singleton
    public SocketClientV3 providerSocketClientV3(final RpcClientProxyInvocationHandlerV2 invocationHandler) {
        return new SocketClientV3(invocationHandler);
    }

    @Provides
    @Singleton
    public SocketServerV1 providerSocketServerV1(final HelloService helloService,
                                                 final SocketRequestHandlerV1 requestHandler) {
        return new SocketServerV1(helloService, requestHandler);
    }


    @Provides
    @Singleton
    public SocketServerV2 providerSocketServerV2(final HelloService helloService,
                                                 final SocketRequestHandlerV1 requestHandler,
                                                 final KryoSerializerV1 kryoSerializerV1) {
        return new SocketServerV2(helloService, requestHandler, kryoSerializerV1);
    }

    @Provides
    @Singleton
    public SocketRequestHandlerV1 provideSocketRequestHandlerV1() {
        return new SocketRequestHandlerV1();
    }

    @Provides
    @Singleton
    public KryoSerializerV1 provideKryoSerializationV1() {
        return new KryoSerializerV1();
    }

    @Provides
    @Singleton
    RpcClientProxyInvocationHandlerV2 proxyInvocationHandlerV2(final KryoSerializerV1 kryoSerializerV1) {
        return new RpcClientProxyInvocationHandlerV2(kryoSerializerV1);
    }
}
