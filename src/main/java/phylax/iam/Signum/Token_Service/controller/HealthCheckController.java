package phylax.iam.Signum.Token_Service.controller;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import phylax.iam.signum.HealthCheckRequest;
import phylax.iam.signum.HealthCheckResponse;
import phylax.iam.signum.HealthCheckServiceGrpc;
import phylax.iam.Signum.Token_Service.service.grpc.HealthCheckService;

/**
 * gRPC controller implementation for the {@link HealthCheckServiceGrpc.HealthCheckServiceImplBase}.
 * <p>
 * This class exposes a health check endpoint over gRPC, allowing clients such as
 * load balancers, service meshes, or monitoring systems to query the service’s
 * availability status.
 * </p>
 * <p>
 * The controller delegates the actual system health verification logic
 * (database and cache checks) to the {@link HealthCheckService}.
 * </p>
 */
@GrpcService
public class HealthCheckController extends HealthCheckServiceGrpc.HealthCheckServiceImplBase {

    @Autowired
    private HealthCheckService healthCheckService;

    private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    /**
     * Handles incoming gRPC health check requests.
     * <p>
     * The method invokes {@link HealthCheckService#runAllChecks()} to verify the
     * health of underlying infrastructure components (database and cache), then
     * streams the result back to the client.
     * </p>
     *
     * @param healthCheckRequest       the incoming gRPC health check request (currently unused,
     *                                 but may carry service-specific health check targets)
     * @param responseStreamObserver   the response stream observer used to send back
     *                                 a {@link HealthCheckResponse}
     */
    @Override
    public void check(HealthCheckRequest healthCheckRequest, StreamObserver<HealthCheckResponse> responseStreamObserver) {
        LoggerUtil.logDebug(logger, "Running Health Check For - " + healthCheckRequest.getService());
        HealthCheckResponse response = healthCheckService.runAllChecks();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
