package phylax.iam.Signum.Token_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 * <p>
 * This class bootstraps the application by delegating to
 * {@link SpringApplication#run(Class, String...)} which sets up
 * the Spring ApplicationContext, auto-configuration, and component scanning.
 * </p>
 */
@SpringBootApplication
public class Application {

	/**
	 * Main method used to launch the Spring Boot application.
	 *
	 * @param args command-line arguments passed at startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
