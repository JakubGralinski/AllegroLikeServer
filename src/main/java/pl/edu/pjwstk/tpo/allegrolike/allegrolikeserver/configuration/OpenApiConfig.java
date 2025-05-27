package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final var server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("AllegroLike Local Development Server");

        final var contact = new Contact();
        contact.setName("Jakub Graliński (s30351) | Mateusz Laskowski (s30613)");
        contact.setEmail("s30351@pjwstk.edu.pl");

        final var info = new Info()
                .title("AllegroLike API")
                .version("1.0")
                .description("This API exposes endpoints related to AllegroLike platform operations and management")
                .contact(contact);

        final var openApi = new OpenAPI()
                .info(info)
                .servers(List.of(server));

        return openApi;
    }
}
