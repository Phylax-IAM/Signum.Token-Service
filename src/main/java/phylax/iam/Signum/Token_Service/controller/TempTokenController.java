package phylax.iam.Signum.Token_Service.controller;

import phylax.iam.Signum.Token_Service.common.util.caching.CacheUtil;
import phylax.iam.Signum.Token_Service.common.util.caching.CacheValue;
import phylax.iam.Signum.Token_Service.service.cache.CacheService;
import phylax.iam.signum.*;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import phylax.iam.Signum.Token_Service.service.grpc.TempTokenService;

/**
 * gRPC controller implementation for handling temporary token operations.
 * <p>
 * This controller serves as the entry point for gRPC requests related to
 * generating and validating temporary tokens. It leverages caching to improve
 * performance by avoiding repeated computation of responses for identical requests.
 * </p>
 *
 * <ul>
 *   <li>{@link #generateTempToken(TempTokenRequest, StreamObserver)} - Generates a temporary token
 *       for a client request, reusing cached results when available.</li>
 *   <li>{@link #validateTempToken(ValidateTempTokenRequest, StreamObserver)} - Validates
 *       a temporary token and associated code, retrieving cached responses when possible.</li>
 * </ul>
 *
 * This class delegates business logic to {@link TempTokenService} and
 * leverages {@link CacheService} for response caching.
 */
@GrpcService
public class TempTokenController extends TempTokenServiceGrpc.TempTokenServiceImplBase {

    /**
     * Service that contains the core business logic for generating and validating temporary tokens.
     */
    @Autowired
    private TempTokenService tempTokenService;

    /**
     * Handles requests to generate a temporary token.
     * <p>
     * This method first checks the cache to see if a response already exists
     * for the provided request. If found, the cached response is returned.
     * Otherwise, a new temporary token is generated via {@link TempTokenService}.
     * </p>
     *
     * @param request          The gRPC request containing information needed to generate a token.
     * @param responseObserver The gRPC stream observer used to return the response to the client.
     */
    @Override
    public void generateTempToken(TempTokenRequest request, StreamObserver<TempTokenResponse> responseObserver) {
        TempTokenResponse response = tempTokenService.generateTempToken(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Handles requests to validate a temporary token and its associated code.
     * <p>
     * Similar to {@link #generateTempToken}, this method checks the cache
     * for a previously computed response. If not found, the request is
     * delegated to {@link TempTokenService} for validation.
     * </p>
     *
     * @param request          The gRPC request containing the token and code to validate.
     * @param responseObserver The gRPC stream observer used to return the validation result.
     */
    @Override
    public void validateTempToken(ValidateTempTokenRequest request, StreamObserver<ValidateTempTokenResponse> responseObserver) {
        ValidateTempTokenResponse response  = tempTokenService.validateTempTokenAndCode(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
