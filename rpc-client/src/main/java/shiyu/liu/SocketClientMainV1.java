package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.client.SocketClientV1;

public class SocketClientMainV1 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketClientV1 socketClientV1 = injector.getInstance(SocketClientV1.class);
        socketClientV1.start();
    }
}
