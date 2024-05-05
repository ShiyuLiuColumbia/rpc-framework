package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.client.SocketClientV2;

public class SocketClientMainV2 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketClientV2 socketClientV2 = injector.getInstance(SocketClientV2.class);
        socketClientV2.start();
    }
}
