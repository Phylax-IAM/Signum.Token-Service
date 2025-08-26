package phylax.iam.Signum.Token_Service.common.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import phylax.iam.Signum.Token_Service.common.aspect.LogExecutionTimeAspect;


/**
 * Annotation to indicate that the execution time of the annotated method
 * should be measured and logged.
 * <p>
 * Methods annotated with {@code @LogExecutionTime} will be intercepted by
 * {@link LogExecutionTimeAspect}, which records the
 * time taken to execute the method and logs it in milliseconds.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * {@code
 * @LogExecutionTime
 * public void processTask() {
 *     // method logic
 * }
 * }
 * </pre>
 *
 * The log output will include the method signature and the execution time,
 * for example:
 * <pre>
 * [Signum-Token-Service] void com.example.MyService.processTask() took 245 ms
 * </pre>
 *
 * @see LogExecutionTimeAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
