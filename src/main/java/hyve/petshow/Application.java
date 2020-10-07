package hyve.petshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static hyve.petshow.util.UrlUtils.*;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
    	return new WebMvcConfigurer() {
    		@Override
    		public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedHeaders("*")
						.allowedOrigins(URL_API_LOCAL, URL_API_LOCAL_DOCKER, URL_API_PROD)
						.allowedMethods("*")
						.allowCredentials(true);
			}
		};
    }
}
