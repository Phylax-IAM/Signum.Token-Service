package phylax.iam.Signum.Token_Service.common.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import phylax.iam.Signum.Token_Service.util.LogUtil;
import phylax.iam.Signum.Token_Service.common.annotation.LogExecutionTime;


/**
 * Aspect for measuring and logging execution time of methods annotated with {@link LogExecutionTime}.
 * <p>
 * This aspect intercepts method execution and records the total time taken
 * by the method. The execution time is logged in milliseconds, along with
 * the method signature, using the application-specific formatting provided
 * by {@link LogUtil}.
 * </p>
 *
 * <h3>Behavior:</h3>
 * <ul>
 *   <li>Records the start time before the method execution.</li>
 *   <li>Executes the target method.</li>
 *   <li>Records the end time after method completion.</li>
 *   <li>Logs the method signature along with the total execution time.</li>
 * </ul>
 *
 * <h3>Example:</h3>
 * <pre>
 * {@code
 * @LogExecutionTime
 * public void performTask() {
 *     // business logic
 * }
 * }
 * </pre>
 *
 * This will produce logs:
 * <pre>
 * [Signum-Token-Service] void com.example.MyService.performTask() took 123 ms
 * </pre>
 */
@Aspect
@Component
public class LogExecutionTimeAspect {

    /**
     * Utility for formatting log messages with service name.
     */
    @Autowired
    private LogUtil logUtil;

    /**
     * Logger for recording execution time messages.
     */
    private final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    /**
     * Advice that measures and logs the execution time of methods annotated with {@link LogExecutionTime}.
     *
     * @param proceedingJoinPoint the join point representing the intercepted method call
     * @return the result of the intercepted method call
     * @throws Throwable if the intercepted method throws any exception
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTimeMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info(logUtil.format(String.format("%s took %d ms", proceedingJoinPoint.getSignature(), (endTime - startTime))));
        return result;
    }
}
