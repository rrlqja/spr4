package song.spring4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Spring4Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring4Application.class, args);
	}

}
