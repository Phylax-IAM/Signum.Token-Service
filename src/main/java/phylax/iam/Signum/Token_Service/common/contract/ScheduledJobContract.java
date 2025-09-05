package phylax.iam.Signum.Token_Service.common.contract;

/**
 * Contract for defining scheduled jobs within the application.
 *
 * <p>This interface establishes a standard abstraction for tasks that are meant to be
 * executed on a recurring schedule. Implementations typically annotate the
 * {@link #execute()} method with scheduling metadata (e.g., using
 * {@code @Scheduled} in Spring) to specify the frequency of execution.</p>
 *
 * <p>Examples of usage include periodic cleanup jobs, cache refresh tasks,
 * or background maintenance routines that run automatically.</p>
 *
 * <h3>Usage Example</h3>
 * <pre>
 * {@code
 * @Component
 * public class RevokedTokenCleanUpJob implements ScheduledJobContract {
 *
 *     @Override
 *     @Scheduled(fixedDelayString = "${scheduler.revoked-token-cleanup.delay:5m}")
 *     public void execute() {
 *         // Job logic here
 *     }
 * }
 * }
 * </pre>
 *
 * @see org.springframework.scheduling.annotation.Scheduled
 */
public interface ScheduledJobContract {

    /**
     * Executes the scheduled job.
     * <p>
     * Implementations should provide the business logic that needs to be performed
     * at the scheduled intervals.
     * </p>
     */
    void execute();

}
