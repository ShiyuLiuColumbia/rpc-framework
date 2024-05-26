package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.client.SocketClientV3;

public class SocketClientMainV4 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketClientV3 socketClientV3 = injector.getInstance(SocketClientV3.class);
        socketClientV3.start();
    }
}
