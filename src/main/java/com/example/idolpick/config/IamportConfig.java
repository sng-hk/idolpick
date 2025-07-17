package com.example.idolpick.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class IamportConfig {

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient("2876188183464346", "d2yzimtnvRwrBxfP76ZaQTdxVh2JGQ31x7WVQWCEME8aZutho15MZnO339m0du8FARlNggpVOJYC54Aq");
    }
}
