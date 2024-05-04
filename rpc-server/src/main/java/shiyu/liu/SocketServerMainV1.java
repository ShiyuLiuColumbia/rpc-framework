package shiyu.liu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import shiyu.liu.module.ServiceImplModule;
import shiyu.liu.module.SocketModule;
import shiyu.liu.socket.server.SocketServerV1;

/*
* Server version 1:
* 1. Use BIO socket programming
* 2. Use java serialization
* 3. Do not have registration service. Just hard code ip address
* 4. No dynamic proxy
* 5. No thread pool, can not handle multiple requests at the same time
* 6. Only 1 hard coded service implementation
* */
public class SocketServerMainV1 {
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceImplModule(), new SocketModule());
        final SocketServerV1 socketServerV1 = injector.getInstance(SocketServerV1.class);
        socketServerV1.start();
    }
}
