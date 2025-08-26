package phylax.iam.Signum.Token_Service.config.exception;

import io.grpc.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GRPCInterceptorConfig {

    @Bean
    public ServerInterceptor exceptionHandlingInterceptor() {
        return new ExceptionHandlingInterceptor();
    }

    public static class ExceptionHandlingInterceptor implements ServerInterceptor {

        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> call,
                Metadata headers,
                ServerCallHandler<ReqT, RespT> next
        ) {
            ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);

            return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(delegate) {

                @Override
                public void onHalfClose() {
                    try{
                        super.onHalfClose();
                    } catch (Exception e) {
                        call.close(
                                Status.INTERNAL
                                        .withDescription("Internal Server Error")
                                        .withCause(e),
                                new Metadata()
                        );
                    }
                }
            };
        }
    }
}
