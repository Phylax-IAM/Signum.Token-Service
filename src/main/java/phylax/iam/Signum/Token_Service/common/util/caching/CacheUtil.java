package phylax.iam.Signum.Token_Service.common.util.caching;

import phylax.iam.signum.TokenRequest;
import phylax.iam.signum.TempTokenRequest;
import phylax.iam.signum.ValidateTokenRequest;
import phylax.iam.signum.ValidateTempTokenRequest;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;


public final class CacheUtil {

    private static final String singleArg = "%s";

    private static final String doubleArg = "%s:%s";

    private static final String tripleArg = "%s:%s:%s";

    private static final String quadrupleArg = "%s:%s:%s:%s";

    private CacheUtil() {
        throw new IllegalInstantiationException();
    }

    public static String toKey(ValidateTokenRequest request) {
        return String.format(
                tripleArg,
                request.getSubject(),
                request.getAuthToken(),
                request.getRefreshToken()
        );
    }

    public static String toKey(TokenRequest request) {
        return String.format(
                doubleArg,
                request.getSubject()
        );
    }

    public static String toKey(TempTokenRequest request) {
        return String.format(
                quadrupleArg,
                request.getSubject(),
                request.getCodeLength(),
                request.getCodeClass(),
                request.getTokenClass()
        );
    }

    public static String toKey(ValidateTempTokenRequest request) {
        return String.format(
                tripleArg,
                request.getCode(),
                request.getToken(),
                request.getSubject()

        );
    }
}
