package phylax.iam.Signum.Token_Service.service.grpc;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import phylax.iam.signum.HealthCheckResponse;
import phylax.iam.signum.ServingStatus;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Service responsible for performing health checks on critical infrastructure
 * components such as the relational database and Redis cache.
 * <p>
 * This service is used by the gRPC {@code HealthCheckService} implementation
 * to verify the availability of dependencies and to return a consolidated
 * health status response.
 * </p>
 */
@Service
public class HealthCheckService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Verifies connectivity to the configured relational database.
     * <p>
     * A simple connection attempt is made, and {@link Connection#isValid(int)}
     * is used to ensure the connection is healthy.
     * </p>
     *
     * @return {@code true} if the database connection is valid; {@code false} otherwise
     */
    private boolean checkDatabase() {
        try(Connection connection = dataSource.getConnection()) {
            return connection.isValid(2);
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Verifies connectivity to the configured Redis cache.
     * <p>
     * The health check executes a {@code PING} command and expects
     * a {@code PONG} response.
     * </p>
     *
     * @return {@code true} if Redis responds with "PONG"; {@code false} otherwise
     */
    private boolean checkCache() {
        try(RedisConnection connection = redisConnectionFactory.getConnection()) {
            return "pong".equalsIgnoreCase(connection.ping());
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Executes all system health checks (database and cache) and builds a
     * {@link HealthCheckResponse} containing the overall service availability.
     * <p>
     * The returned status and message indicate the combined health state:
     * <ul>
     *   <li>{@code ALL_SYSTEMS_UP} if both DB and cache are healthy.</li>
     *   <li>{@code DATABASE_DOWN} if the cache is healthy but DB is unavailable.</li>
     *   <li>{@code CACHE_DOWN} if the DB is healthy but cache is unavailable.</li>
     *   <li>{@code Unexpected error occurred} if neither system is healthy or an interceptor is thrown.</li>
     * </ul>
     * </p>
     *
     * @return a {@link HealthCheckResponse} with {@link ServingStatus#SERVING} if all systems are up,
     *         otherwise {@link ServingStatus#NOT_SERVING} with a descriptive message
     */
    public HealthCheckResponse runAllChecks() {
        String statusMessage = "UNEXPECTED_ERROR_OCCURRED";
        ServingStatus status = ServingStatus.NOT_SERVING;

        try {
            final boolean dbOk = checkDatabase();
            final boolean cacheOk = checkCache();

            if(dbOk && cacheOk) {
                status = ServingStatus.SERVING;
                statusMessage = "ALL_SYSTEMS_UP";
            } else if(cacheOk) {
                statusMessage = "DATABASE_DOWN";
            } else if(dbOk) {
                statusMessage = "CACHE_DOWN";
            }
            return HealthCheckResponse
                    .newBuilder()
                    .setStatus(status)
                    .setDetails(statusMessage)
                    .build();
        } catch (Exception e) {
            LoggerUtil.logErrorAndDebug(
                    LoggerFactory.getLogger(HealthCheckService.class),
                    "",
                    e
            );

            return HealthCheckResponse
                    .newBuilder()
                    .setStatus(status)
                    .setDetails(statusMessage)
                    .build();
        }
    }
}
