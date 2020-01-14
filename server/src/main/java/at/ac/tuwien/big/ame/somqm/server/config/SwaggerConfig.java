package at.ac.tuwien.big.ame.somqm.server.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

  private static final ModelRef ERROR_MODEL = new ModelRef("string");

  private static ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("SOMQM REST API")
        .description("Service-Oriented Management And Querying Of UML-Models.")
        .version("1.0.0")
        .build();
  }

  private static void setGlobalResponseMessages(Docket docket) {
    List<ResponseMessage> responseMessages = Arrays.asList(
        new ResponseMessageBuilder()
            .code(400)
            .message("Bad request: wrong API usage or element already existing")
            .responseModel(ERROR_MODEL)
            .build(),
        new ResponseMessageBuilder()
            .code(404)
            .message("Element not found")
            .responseModel(ERROR_MODEL)
            .build(),
        new ResponseMessageBuilder()
            .code(500)
            .message("Server error")
            .responseModel(ERROR_MODEL)
            .build()
    );
    for (RequestMethod requestMethod : RequestMethod.values()) {
      docket.globalResponseMessage(requestMethod, responseMessages);
    }
  }

  @Bean
  public Docket productApi() {
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("at.ac.tuwien.big.ame.somqm.server.rest"))
        .paths(PathSelectors.any())
        .build();
    setGlobalResponseMessages(docket);
    return docket;
  }

}
