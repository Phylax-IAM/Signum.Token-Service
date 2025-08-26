package phylax.iam.Signum.Token_Service.config.env;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Spring-managed component for accessing environment variables and application
 * properties in a safe and convenient way.
 * <p>
 * This class leverages Spring's {@link Environment} abstraction to retrieve
 * configuration values from multiple sources (e.g., environment variables,
 * {@code application.properties}, system properties).
 * </p>
 */
@Component
public class EnvConfig {

    /**
     * Spring {@link Environment} providing access to configuration properties.
     * <p>
     * This field is automatically injected by Spring at runtime.
     * </p>
     */
    private Environment environment;

    /**
     * Retrieves the value of the given environment variable or property key.
     *
     * @param envVarKey the name of the environment variable or property key;
     *                  must not be {@code null}
     * @return an {@link Optional} containing the property value if found,
     *         or {@link Optional#empty()} if no value is defined
     */
    public Optional<String> getEnv(String envVarKey) {
        return Optional.ofNullable(environment.getProperty(envVarKey));
    }
}
