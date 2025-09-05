package phylax.iam.Signum.Token_Service.service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phylax.iam.Signum.Token_Service.common.constant.TokenClassConstant;
import phylax.iam.Signum.Token_Service.common.util.id.UUIDUtil;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;
import phylax.iam.Signum.Token_Service.entity.key.ActiveTokenKey;
import phylax.iam.Signum.Token_Service.repository.UserActiveTokenRepository;
import phylax.iam.Signum.Token_Service.repository.UserRevokedTokenRepository;
import phylax.iam.signum.*;

import java.util.UUID;

@Service
public class AuthTokenService {

    @Autowired
    private UserActiveTokenRepository userActiveTokenRepository;

    @Autowired
    private UserRevokedTokenRepository userRevokedTokenRepository;

    private final Logger logger = LoggerFactory.getLogger(AuthTokenService.class);

//    message ValidateTokenRequest {
//        string subject = 1;
//        string authToken = 2;
//        string refreshToken = 3;
//    }
//
//    message ValidateTokenResponse {
//        bool isValid = 1;
//        string authToken = 2;
//        string refreshToken = 3;
//    }
//
//    message TokenResponse {
//        string authToken = 1;
//        string refreshToken = 2;
//    }
//
//    message TokenRequest {
//        string subject = 1;
//    }
//
//    message RevokeResponse {
//        bool isRevoked = 1
//    }
//

    @Transactional
    private boolean moveAndDeleteTokenFor(UUID subject) {
        boolean successfulDeletion = true;

        try {
            // fetch active tokens
            long activeTokenCount = userActiveTokenRepository.countBySubject(subject);

            // if no active token exists for this subject return true
            if(activeTokenCount == 0) {
                return successfulDeletion;
            }

            // fetch the tokens


        } catch (Exception e) {
            successfulDeletion = false;
            LoggerUtil.logErrorAndDebug(logger, "Failed to delete the token", e);
        }
        return successfulDeletion;
    }

    @Transactional
    public TokenResponse generateAuthToken(TokenRequest request) {

        // verify and extract uuid
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );

        // create token key
        final ActiveTokenKey activeTokenKey = ActiveTokenKey
                .builder()
                .subject(subject)
                .tokenClassConstant(TokenClassConstant.AUTHENTICATION)
                .build();

        // check if token already exists and if it does then return it

        // create a new token

        // create a new token entity and save it

        // return the response
        return TokenResponse
                .newBuilder()
                .build();
    }

    public TokenResponse refreshAuthToken(ValidateTokenRequest request) {
        return null;
    }

    public ValidateTokenResponse validateAuthToken(ValidateTokenResponse request) {
        return null;
    }

    public RevokeResponse revokeAuthToken(TokenRequest request) {

        // verify and extract uuid
        final UUID subject = UUIDUtil.verifyIsStringUUIDTargetVersion(
                request.getSubject(),
                7
        );
        boolean isRevoked = this.moveAndDeleteTokenFor(subject);
        return RevokeResponse
                .newBuilder()
                .setIsRevoked(isRevoked)
                .build();
    }
}
