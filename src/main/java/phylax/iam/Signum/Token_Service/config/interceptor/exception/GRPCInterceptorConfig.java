package phylax.iam.Signum.Token_Service.config.interceptor.exception;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.grpc.server.GlobalServerInterceptor;
import phylax.iam.Signum.Token_Service.common.exception.*;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;

/**
 * Spring configuration class that provides a global gRPC interceptor for interceptor handling.
 * <p>
 * This interceptor catches exceptions thrown by gRPC service methods and translates them into
 * appropriate gRPC {@link Status} codes sent to the client, ensuring that the client receives
 * structured error information instead of crashing the server.
 * <p>
 * Exceptions are logged using {@link LoggerUtil} before the RPC is closed.
 */
@Configuration
public class GRPCInterceptorConfig {

    private static final Logger logger = LoggerFactory.getLogger(GRPCInterceptorConfig.class);

    /**
     * Registers the global interceptor handling interceptor as a Spring Bean.
     *
     * @return a {@link ServerInterceptor} that handles exceptions thrown by gRPC service methods.
     */
    @Bean
    @Order(100)
    @GlobalServerInterceptor
    public ServerInterceptor exceptionHandlingInterceptor() {
        return new ExceptionHandlingInterceptor();
    }

    /**
     * A gRPC {@link ServerInterceptor} that catches exceptions thrown during RPC execution.
     * <p>
     * It wraps the {@link ServerCall.Listener} using a {@link ForwardingServerCallListener.SimpleForwardingServerCallListener}
     * and overrides {@link #onHalfClose()} to intercept service method calls. Exceptions are
     * mapped to gRPC {@link Status} codes:
     * <ul>
     *     <li>{@link HashingException}, {@link PayloadStringException}, {@link UUIDException} → {@link Status#INVALID_ARGUMENT}</li>
     *     <li>{@link IllegalCodeLengthException} → {@link Status#OUT_OF_RANGE}</li>
     *     <li>All other exceptions → {@link Status#INTERNAL}</li>
     * </ul>
     * Each interceptor is also logged using {@link LoggerUtil}.
     */
    public static class ExceptionHandlingInterceptor implements ServerInterceptor {

        /**
         * Intercepts incoming gRPC calls and wraps the listener to handle exceptions.
         *
         * @param call    the server call
         * @param headers the metadata of the call
         * @param next    the next call handler in the interceptor chain
         * @param <ReqT>  the type of request message
         * @param <RespT> the type of response message
         * @return a {@link ServerCall.Listener} that handles exceptions thrown during RPC execution
         */
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call,
                Metadata headers,
                ServerCallHandler<ReqT, RespT> next
        ) {
            ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);

            return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(delegate) {

                /**
                 * Invoked when the client has finished sending messages.
                 * <p>
                 * Wraps the actual service execution in a try-catch block to:
                 * <ul>
                 *     <li>Log the interceptor using {@link LoggerUtil}</li>
                 *     <li>Close the RPC call with the appropriate {@link Status} code</li>
                 * </ul>
                 */
                @Override
                public void onHalfClose() {
                    try{
                        super.onHalfClose();
                    } catch (InvalidJWTException e) {
                        final String message = "Invalid JWT Provided";
                        LoggerUtil.logErrorAndDebug(logger, "", e);
                        call.close(
                                Status.UNAUTHENTICATED
                                        .withDescription(message),
                                new Metadata()
                        );
                    } catch (HashingException | PayloadStringException | UUIDException e) {
                        final String message = e.getMessage();
                        LoggerUtil.logErrorAndDebug(logger, message, e);
                        call.close(
                                Status.INVALID_ARGUMENT
                                        .withDescription(message),
                                new Metadata()
                        );

                    } catch (IllegalCodeLengthException e) {
                        final String message = e.getMessage();
                        LoggerUtil.logErrorAndDebug(logger, message, e);
                        call.close(
                                Status.OUT_OF_RANGE
                                        .withDescription(message),
                                new Metadata()
                        );

                    } catch (Exception e) {
                        final String message = "Internal Server Error";
                        // logging the error using the standard logger
                        LoggerUtil.logErrorAndDebug(logger, message, e);
                        call.close(
                                Status.INTERNAL
                                        .withDescription(message),
                                new Metadata()
                        );
                    }
                }
            };
        }
    }
}
