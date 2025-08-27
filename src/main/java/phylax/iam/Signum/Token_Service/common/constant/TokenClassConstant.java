package phylax.iam.Signum.Token_Service.common.constant;

/**
 * Represents the different classes of tokens supported by the
 * <b>Signum Token Service</b>.
 *
 * <p>
 * Each token class serves a specific role in authentication,
 * authorization, and session management workflows.
 * </p>
 *
 * <ul>
 *   <li>{@link #AUTHENTICATION} –
 *       A short-lived access token used to authenticate user requests
 *       against protected resources.</li>
 *
 *   <li>{@link #REFRESH} –
 *       A long-lived token used to obtain new {@link #AUTHENTICATION} tokens
 *       without requiring the user to log in again.</li>
 *
 *   <li>{@link #TEMPORARY} –
 *       A short-lived token used for one-off or sensitive operations
 *       such as password reset, email verification, or
 *       multi-factor authentication (MFA).</li>
 * </ul>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * TokenClassConstant type = TokenClassConstant.AUTHENTICATION;
 * if (type == TokenClassConstant.REFRESH) {
 *     // Handle refresh token logic
 * }
 * }</pre>
 */
public enum TokenClassConstant {

    /**
     * Short-lived token for authenticating user requests.
     */
    AUTHENTICATION,

    /**
     * Long-lived token for renewing authentication tokens
     * without re-login.
     */
    REFRESH,

    /**
     * Special-purpose short-lived token for sensitive
     * workflows like password reset or MFA.
     */
    TEMPORARY
}
