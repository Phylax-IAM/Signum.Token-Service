package phylax.iam.Signum.Token_Service.config.cache;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


/**
 * {@code RedisConfig} is a Spring configuration class for setting up Redis connectivity
 * and serialization using Lettuce as the Redis client.
 * <p>
 * It defines beans for {@link LettuceConnectionFactory} and {@link RedisTemplate},
 * ensuring that Redis keys and values are serialized consistently using
 * {@link StringRedisSerializer} for keys and {@link GenericJackson2JsonRedisSerializer}
 * for values.
 * </p>
 *
 * <p>Configuration properties are injected from {@code application.properties} or
 * {@code application.yml}, using the {@code spring.redis.*} namespace.</p>
 *
 * Example properties:
 * <pre>
 * spring.redis.user=myuser
 * spring.redis.host=localhost
 * spring.redis.port=6379
 * spring.redis.password=secret
 * </pre>
 *
 * @author YourName
 */
@Getter
@Configuration
public class RedisConfig {

    /** Redis username (if authentication is enabled). */
    @Value("${spring.redis.user:user}")
    private String user;

    /** Redis host (default: localhost). */
    @Value("${spring.redis.host:localhost}")
    private String host;

    /** Redis port (default: 6379). */
    @Value("${spring.redis.port:6379}")
    private String port;

    /** Redis password (if authentication is enabled). */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * Creates a {@link LettuceConnectionFactory} for connecting to a standalone Redis instance.
     * <p>
     * It uses the configured host, port, and password from application properties.
     * </p>
     *
     * @return a fully configured {@link LettuceConnectionFactory} bean
     */
    @Bean
    public LettuceConnectionFactory redisLettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(this.host, Integer.parseInt(this.getPort()));
        redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * Creates and configures a {@link RedisTemplate} for Redis operations.
     * <p>
     * The template is configured with:
     * <ul>
     *   <li>{@link StringRedisSerializer} for keys and hash keys</li>
     *   <li>{@link GenericJackson2JsonRedisSerializer} for values and hash values</li>
     *   <li>{@link GenericJackson2JsonRedisSerializer} as the default serializer</li>
     * </ul>
     * </p>
     *
     * @param redisConnectionFactory the Redis connection factory
     * @return a fully configured {@link RedisTemplate} bean
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("redisLettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        final GenericJackson2JsonRedisSerializer defaultAndValueSerializer = new GenericJackson2JsonRedisSerializer();
        final StringRedisSerializer keySerializer = new StringRedisSerializer();

        // default serializer
        redisTemplate.setDefaultSerializer(defaultAndValueSerializer);

        // setting the key and value serializer
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(defaultAndValueSerializer);

        // setting the hash key and value serializer
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(defaultAndValueSerializer);

        // informing spring boot that everything is set up and we can go ahead
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

