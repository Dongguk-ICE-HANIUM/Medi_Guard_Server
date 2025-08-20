package hanium.dongguk.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient googleWebClient(){
        return WebClient.builder()
                .baseUrl("https://www.googleapis.com/oauth2/v1")
                .build();
    }
}
