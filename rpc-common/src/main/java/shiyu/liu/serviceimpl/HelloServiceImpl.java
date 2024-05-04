package shiyu.liu.serviceimpl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shiyu.liu.Hello;
import shiyu.liu.HelloService;

@Slf4j
@NoArgsConstructor
public class HelloServiceImpl implements HelloService {
    @Override
    public String Hello(Hello hello) {
        log.info("Received hello message: {}", hello);
        final String response = "Received hello message: " + hello.getMessage();
        return response;
    }
}
