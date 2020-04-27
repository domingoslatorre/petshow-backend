package hyve.petshow.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {
	@Test
	public void contextLoads() {
		boolean teste = true;

		assertEquals(true, teste);
	}

}
