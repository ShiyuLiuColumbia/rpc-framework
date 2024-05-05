package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.server.SocketServerV1;

/*
* Server version 2:
* Same as version 1, but client version 2 has dynamic proxy
* */
public class SocketServerMainV2 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketServerV1 socketServerV1 = injector.getInstance(SocketServerV1.class);
        socketServerV1.start();
    }
}
