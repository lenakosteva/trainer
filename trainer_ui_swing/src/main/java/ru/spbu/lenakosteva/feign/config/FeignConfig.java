package ru.spbu.lenakosteva.feign.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import jakarta.annotation.PostConstruct;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spbu.lenakosteva.feign.client.QuestionFeignClient;

import java.util.concurrent.TimeUnit;

@EnableFeignClients
@Configuration
public class FeignConfig {

    ObjectMapper om;

    @PostConstruct
    public void init() {
        om = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public QuestionFeignClient questionApi() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(om))
                .decoder(new JacksonDecoder(om))
                .options(new Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
                .decode404()
                .target(QuestionFeignClient.class, "http://localhost:8081");
    }
}
