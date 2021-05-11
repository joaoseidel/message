package io.joaoseidel.message;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessageApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageApplication.class, args);
  }

  public @Bean OpenAPI messageApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Messages API")
                .description("A simple message scheduler application")
                .version("v1.0.0")
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
  }
}
