package phylax.iam.Signum.Token_Service.common.constant;

/**
 * Represents the different types of tokens supported by the Signum Token Service.
 * <p>
 * Each token type serves a distinct purpose in authentication, authorization,
 * and session management workflows.
 * </p>
 *
 * <ul>
 *   <li><b>AUTHENTICATION</b> – A short-lived access token used to authenticate user requests.</li>
 *   <li><b>REFRESH</b> – A long-lived token used to generate new authentication tokens
 *       without requiring the user to log in again.</li>
 *   <li><b>Temporary</b> – A short-lived token used for special operations such as
 *       password reset, email verification, or multi-factor authentication (MFA).</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * TokenType type = TokenType.AUTHENTICATION;
 * if (type == TokenType.REFRESH) {
 *     // handle REFRESH logic
 * }
 * }</pre>
 * </p>
 */
public enum TokenType {

    AUTHENTICATION,

    REFRESH,

    TEMPORARY

}
