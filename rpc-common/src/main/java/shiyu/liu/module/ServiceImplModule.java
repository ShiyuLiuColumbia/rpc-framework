package shiyu.liu.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import shiyu.liu.HelloService;
import shiyu.liu.serviceimpl.HelloServiceImpl;

public class ServiceImplModule extends AbstractModule {
    @Provides
    @Singleton
    public HelloService providerHelloService() {
        return new HelloServiceImpl();
    }
}
