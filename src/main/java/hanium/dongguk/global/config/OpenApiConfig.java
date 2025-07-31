package hanium.dongguk.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(components())
                            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("Medi Guard API 명세서")
                         .description("테스트 상황 시에만 해당 문서가 나타나야 합니다.")
                         .version("1.0.0");
    }

    private Components components() {
        String jwtSchemeName = "JWT";

        return new Components() .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));
    }
}