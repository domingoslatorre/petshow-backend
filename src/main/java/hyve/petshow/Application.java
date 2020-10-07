package hyve.petshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
						.allowedOrigins("http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200")
						.allowedMethods("*")
						.allowCredentials(true);
			}
		};
    }
}
