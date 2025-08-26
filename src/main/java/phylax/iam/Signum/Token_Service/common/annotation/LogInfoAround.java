package phylax.iam.Signum.Token_Service.common.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Custom annotation for logging messages around method execution
 * using Spring AOP (Aspect-Oriented Programming).
 * <p>
 * When applied to a method, this annotation can be intercepted by
 * an aspect (e.g., using {@code @Around} advice) to log messages
 * before and after the method invocation.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 *  @LogInfoAround(
 *      logBefore = "Starting token validation",
 *      logAfter = "Finished token validation"
 *  )
 *  public void validateToken() {
 *      // business logic
 *  }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * Typical use cases include:
 * <ul>
 *   <li>Tracing method execution flow.</li>
 *   <li>Debugging service-layer methods.</li>
 *   <li>Adding contextual log messages without manual boilerplate logging.</li>
 * </ul>
 * </p>
 *
 * @see org.aspectj.lang.annotation.Around
 * @see org.springframework.stereotype.Component
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfoAround {

    /**
     * Message to be logged before the annotated method executes.
     *
     * @return the log message to display before method execution
     */
    String logBefore() default "Running Log Before";

    /**
     * Message to be logged after the annotated method executes.
     *
     * @return the log message to display after method execution
     */
    String logAfter() default "Running Log After";
}
