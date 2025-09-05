package phylax.iam.Signum.Token_Service.controller;

import phylax.iam.signum.*;
import phylax.iam.Signum.Token_Service.service.cache.CacheService;
import phylax.iam.Signum.Token_Service.common.util.caching.CacheValue;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import phylax.iam.Signum.Token_Service.service.grpc.AuthTokenService;

@GrpcService
public class AuthTokenController extends AuthTokenServiceGrpc.AuthTokenServiceImplBase {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private CacheService<String, CacheValue> cacheService;

    @Override
    public void generateAuthToken(TokenRequest request, StreamObserver<TokenResponse> responseObserver) {
        super.generateAuthToken(request, responseObserver);
    }

    @Override
    public void refreshAuthToken(ValidateTokenRequest request, StreamObserver<TokenResponse> responseObserver) {
        super.refreshAuthToken(request, responseObserver);
    }

    @Override
    public void validateAuthToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        super.validateAuthToken(request, responseObserver);
    }

    @Override
    public void revokeAuthToken(TokenRequest request, StreamObserver<RevokeResponse> responseObserver) {
        super.revokeAuthToken(request, responseObserver);
    }
}
