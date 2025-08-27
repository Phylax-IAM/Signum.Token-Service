package phylax.iam.Signum.Token_Service.common.util.secret;

import java.security.SecureRandom;

import phylax.iam.Signum.Token_Service.common.exception.IllegalCodeLengthException;


/**
 * Utility class for generating random codes such as numeric, alphabetical,
 * and alphanumeric values.
 * <p>
 * This class uses a cryptographically strong {@link SecureRandom} instance
 * to ensure unpredictability of generated codes.
 * It is designed as a non-instantiable static utility class.
 * </p>
 */
public final class CodeUtil {

    /** Secure random number generator for code generation. */
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException always, since this is a utility class
     */
    private CodeUtil() {
        throw new UnsupportedOperationException("CodeUtil is a utility class and cannot be instantiated.");
    }

    /**
     * Validates that the given {@code codeLength} is within the supported range.
     *
     * @param codeLength the requested code length
     * @throws IllegalCodeLengthException if {@code codeLength <= 0} or {@code codeLength > 128}
     */
    private static void validateCodeLength(int codeLength) {
        if (codeLength <= 0 || codeLength > 128) {
            throw new IllegalCodeLengthException();
        }
    }

    /**
     * Generates a random integer code of the specified length.
     * <p>
     * The resulting integer will have at most {@code codeLength} digits,
     * but may contain fewer if the leading digit(s) are zero.
     * </p>
     *
     * @param codeLength the number of digits in the generated integer code
     * @return a random integer value with up to {@code codeLength} digits
     * @throws IllegalCodeLengthException if {@code codeLength <= 0} or {@code codeLength > 128}
     */
    private static int generateIntegerCode(int codeLength) {
        validateCodeLength(codeLength);
        return secureRandom.nextInt((int) Math.pow(10, codeLength));
    }

    /**
     * Generates a zero-padded numeric code as a string of the specified length.
     * <p>
     * Example: with {@code codeLength = 5}, a generated code could be {@code "00429"}.
     * </p>
     *
     * @param codeLength the length of the numeric string to generate
     * @return a zero-padded numeric code string of length {@code codeLength}
     * @throws IllegalCodeLengthException if {@code codeLength <= 0} or {@code codeLength > 128}
     */
    public static String generateIntegerCodeAsString(int codeLength) {
        validateCodeLength(codeLength);
        return String.format("%0" + codeLength + "d", generateIntegerCode(codeLength));
    }

    /**
     * Generates a random alphabetical string of the specified length.
     * <p>
     * Characters are randomly selected as either uppercase (A–Z) or lowercase (a–z).
     * </p>
     *
     * @param codeLength the number of characters in the generated code
     * @return a random alphabetical string of length {@code codeLength}
     * @throws IllegalCodeLengthException if {@code codeLength <= 0} or {@code codeLength > 128}
     */
    public static String generateAlphabeticalCode(int codeLength) {
        validateCodeLength(codeLength);

        StringBuilder code = new StringBuilder();
        while (codeLength-- > 0) {
            boolean isUpper = secureRandom.nextBoolean();
            code.append((char) (isUpper ? 65 + secureRandom.nextInt(26) : 97 + secureRandom.nextInt(26)));
        }
        return code.toString();
    }

    /**
     * Generates a random alphanumeric string of the specified length.
     * <p>
     * Each character is randomly chosen as either:
     * <ul>
     *   <li>a digit (0–9),</li>
     *   <li>an uppercase letter (A–Z), or</li>
     *   <li>a lowercase letter (a–z).</li>
     * </ul>
     * </p>
     *
     * @param codeLength the number of characters in the generated code
     * @return a random alphanumeric string of length {@code codeLength}
     * @throws IllegalCodeLengthException if {@code codeLength <= 0} or {@code codeLength > 128}
     */
    public static String generateAlphaNumericCode(int codeLength) {
        validateCodeLength(codeLength);

        StringBuilder code = new StringBuilder();
        while (codeLength-- > 0) {
            boolean isInteger = secureRandom.nextBoolean();
            if (isInteger) {
                code.append(secureRandom.nextInt(10));
            } else {
                boolean isUpper = secureRandom.nextBoolean();
                code.append((char) (isUpper ? 65 + secureRandom.nextInt(26) : 97 + secureRandom.nextInt(26)));
            }
        }
        return code.toString();
    }
}

