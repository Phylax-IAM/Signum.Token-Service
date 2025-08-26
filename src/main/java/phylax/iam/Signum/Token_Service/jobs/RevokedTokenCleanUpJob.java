package phylax.iam.Signum.Token_Service.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import phylax.iam.Signum.Token_Service.common.annotation.LogExecutionTime;
import phylax.iam.Signum.Token_Service.common.annotation.LogInfoAround;
import phylax.iam.Signum.Token_Service.common.contract.ScheduledJobContract;
import phylax.iam.Signum.Token_Service.repository.UserRevokedTokenRepository;
import phylax.iam.Signum.Token_Service.common.util.LogUtil;

import java.time.Instant;

/**
 * Scheduled job responsible for cleaning up expired revoked tokens
 * from the system.
 *
 * <p>This job periodically deletes records from the revoked tokens
 * repository where the expiration time has passed. The execution interval
 * is configurable via the {@code application.yaml} property:
 * <pre>
 * scheduler:
 *   revoked-token-cleanup:
 *     delay: 5m
 * </pre>
 * The default delay is {@code 5 minutes} if the property is not defined.</p>
 *
 * <p>Additional aspects applied to this job include:
 * <ul>
 *   <li>{@link LogInfoAround} – Logs entry and exit messages for the job execution</li>
 *   <li>{@link LogExecutionTime} – Captures and logs the execution time of the job</li>
 * </ul>
 * </p>
 *
 * <p>In case the {@link UserRevokedTokenRepository} is not initialized,
 * a warning is logged and the cleanup is skipped.</p>
 *
 * @see UserRevokedTokenRepository
 * @see ScheduledJobContract
 */
@Component
public class RevokedTokenCleanUpJob implements ScheduledJobContract {

    @Autowired
    private UserRevokedTokenRepository userRevokedTokenRepository;

    @Autowired
    private LogUtil logUtil;

    private final Logger logger = LoggerFactory.getLogger(RevokedTokenCleanUpJob.class);

    /**
     * Executes the cleanup job that removes all expired revoked tokens.
     *
     * <p>The method is executed at a fixed delay, configured via
     * {@code scheduler.revoked-token-cleanup.delay} in
     * {@code application.yaml}. The default delay is 5 minutes.</p>
     *
     * <p>If the repository is not yet initialized, a warning log entry
     * is generated and the job is skipped for that run.</p>
     */
    @Override
    @Scheduled(
            fixedDelayString = "${scheduler.revoked-token-cleanup.delay:5m}"
    )
    @LogInfoAround(
            logBefore = "Running RevokedTokenCleanUpJob with the time delay:" + "${scheduler.revoked-token-cleanup.delay:5m}",
            logAfter = "RevokedTokenCleanUpJob ran successfully"
    )
    @LogExecutionTime
    public void execute() {

        if(this.userRevokedTokenRepository == null) {
            logger.warn(logUtil.format("The userRevokedTokenRepository is not yet initialized"));
        } else {
            this.userRevokedTokenRepository.deleteExpiredToken(
                    Instant.now()
            );
        }
    }
}
