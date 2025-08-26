package phylax.iam.Signum.Token_Service.common.aspect;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import phylax.iam.Signum.Token_Service.common.util.LogUtil;
import phylax.iam.Signum.Token_Service.common.annotation.LogInfoAround;

/**
 * Aspect for handling logging around methods annotated with {@link LogInfoAround}.
 * <p>
 * This aspect intercepts method execution and logs configurable messages
 * before and after the method runs. The log messages are defined in the
 * {@link LogInfoAround} annotation applied to the target method.
 * </p>
 *
 * <h3>Behavior:</h3>
 * <ul>
 *   <li>Logs a "before" message before the method executes.</li>
 *   <li>Proceeds with the actual method execution.</li>
 *   <li>Logs an "after" message once the method completes.</li>
 * </ul>
 *
 * <p>
 * The actual log messages are formatted using {@link LogUtil} to prepend the
 * application service name (configured via <code>spring.application.name</code>).
 * </p>
 *
 * <h3>Example:</h3>
 * <pre>
 * {@code
 * @LogInfoAround(logBefore = "Starting process", logAfter = "Process finished")
 * public void myMethod() {
 *     // business logic
 * }
 * }
 * </pre>
 *
 * This will produce logs:
 * <pre>
 * [Signum-Token-Service] Starting process
 * [Signum-Token-Service] Process finished
 * </pre>
 */
@Aspect
@Component
public class LogInfoAroundAspect {

    /**
     * Utility for formatting log messages with service name.
     */
    @Autowired
    private LogUtil logUtil;

    /**
     * Logger for recording log messages.
     */
    private final Logger logger = LoggerFactory.getLogger(LogInfoAroundAspect.class);

    /**
     * Advice that runs around methods annotated with {@link LogInfoAround}.
     * <p>
     * Logs the {@link LogInfoAround#logBefore()} message before method execution,
     * executes the target method, and then logs the {@link LogInfoAround#logAfter()}
     * message after method execution.
     * </p>
     *
     * @param proceedingJoinPoint the join point representing the intercepted method call
     * @param logInfoAround the annotation instance containing log message configuration
     * @return the result of the intercepted method call
     * @throws Throwable if the intercepted method throws any exception
     */
    @Around("@annotation(LogInfoAround)")
    public Object logInfoAroundMethod(ProceedingJoinPoint proceedingJoinPoint, LogInfoAround logInfoAround) throws Throwable {
        logger.info(logUtil.format(logInfoAround.logBefore()));
        Object result = proceedingJoinPoint.proceed();
        logger.info(logUtil.format(logInfoAround.logAfter()));
        return result;
    }
}
