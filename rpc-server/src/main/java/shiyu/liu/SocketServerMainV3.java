package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.server.SocketServerV1;
import shiyu.liu.socket.server.SocketServerV2;

/*
* Server version 2:
* Same as version 1, but client version 2 has dynamic proxy
* */
public class SocketServerMainV3 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketServerV2 socketServerV2 = injector.getInstance(SocketServerV2.class);
        socketServerV2.start();
    }
}
