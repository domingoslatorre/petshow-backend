package hyve.petshow;

import hyve.petshow.config.ApplicationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ApplicationProperties applicationProperties;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		log.info(applicationProperties.getEnvName());
	}
}
