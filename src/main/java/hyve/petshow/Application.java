package hyve.petshow;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Log4j2
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//    	return new WebMvcConfigurer() {
//    		@Override
//    		public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").
//				allowedOrigins("http://0.0.0.0:4200", "https://petshow-frontend.herokuapp.com")
//				.allowedHeaders("*");
//			}
//		};
//    }
}
