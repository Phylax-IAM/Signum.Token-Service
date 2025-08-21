package phylax.iam.Signum.Token_Service;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import phylax.iam.signum.TokenRequest;
import phylax.iam.signum.TokenResponse;
import phylax.iam.signum.TokenServiceGrpc;

@GrpcService
public class TokenServiceImpl extends TokenServiceGrpc.TokenServiceImplBase {

    @Override
    public void generateToken(TokenRequest request, StreamObserver<TokenResponse> responseObserver) {
        final String id = request.getUserId();

        TokenResponse response = TokenResponse.newBuilder()
                .setToken("Token is for - " + id)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
