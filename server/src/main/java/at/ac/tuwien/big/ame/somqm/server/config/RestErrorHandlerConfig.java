package at.ac.tuwien.big.ame.somqm.server.config;

import at.ac.tuwien.big.ame.somqm.server.rest.RestErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestErrorHandlerConfig {

  @Bean
  public RestErrorHandler exceptionHandler() {
    return new RestErrorHandler();
  }

}
