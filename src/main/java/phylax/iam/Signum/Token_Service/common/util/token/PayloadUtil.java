package phylax.iam.Signum.Token_Service.common.util.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import phylax.iam.Signum.Token_Service.common.exception.JsonException;
import phylax.iam.Signum.Token_Service.dto.payload.AuthTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.dto.payload.TempTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.dto.payload.RefreshTokenPayloadDTO;
import phylax.iam.Signum.Token_Service.common.exception.PayloadStringException;
import phylax.iam.Signum.Token_Service.common.exception.IllegalInstantiationException;


/**
 * Utility class for handling token payload serialization and deserialization.
 * <p>
 * This class provides static methods to convert different token payload DTOs
 * ({@link AuthTokenPayloadDTO}, {@link RefreshTokenPayloadDTO}, and {@link TempTokenPayloadDTO})
 * into JSON strings (for embedding in tokens) and to parse JSON strings back
 * into their respective DTO objects.
 * </p>
 *
 * <p>It leverages the Jackson {@link com.fasterxml.jackson.databind.ObjectMapper}
 * for JSON processing. Any failures during conversion are wrapped in a custom
 * {@link JsonException}.</p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * AuthTokenPayloadDTO dto = new AuthTokenPayloadDTO("user123", "tenantA");
 * String json = PayloadUtil.generateAuthPayload(dto);
 *
 * AuthTokenPayloadDTO parsed = PayloadUtil.extractAuthPayload(json);
 * }</pre>
 *
 * <p>This is a standalone static utility class and cannot be instantiated.</p>
 *
 * @author Pragyanshu Rai
 * @since 1.0
 */
public final class PayloadUtil {

    /**
     * Singleton Jackson {@link ObjectMapper} used for JSON serialization
     * and deserialization. Jackson's ObjectMapper is thread-safe after
     * configuration, so this single instance is safe to use.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Private constructor to prevent instantiation.
     */
    private PayloadUtil() {
        throw new IllegalInstantiationException();
    }

    private static void validatePayloadString(String payloadString) {
        if (payloadString == null || payloadString.isEmpty()) {
            throw new PayloadStringException();
        }
    }

    /**
     * Converts the given {@link AuthTokenPayloadDTO} object into its JSON string representation.
     *
     * @param authTokenPayloadDTO the payload object to convert
     * @return the JSON string representation of the payload
     * @throws JsonException if the conversion fails
     */
    public static String generateAuthPayload(AuthTokenPayloadDTO authTokenPayloadDTO) {
        try {
            return objectMapper.writeValueAsString(authTokenPayloadDTO);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to serialize AuthTokenPayloadDTO");
        }
    }

    /**
     * Parses a JSON string into an {@link AuthTokenPayloadDTO} object.
     *
     * @param tokenPayloadJsonString the JSON string representing the payload
     * @return the parsed {@link AuthTokenPayloadDTO} object
     * @throws JsonException if the parsing fails
     */
    public static AuthTokenPayloadDTO extractAuthPayload(String tokenPayloadJsonString) {
        validatePayloadString(tokenPayloadJsonString);

        try {
            return objectMapper.readValue(tokenPayloadJsonString, AuthTokenPayloadDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to deserialize AuthTokenPayloadDTO");
        }
    }

    /**
     * Converts the given {@link RefreshTokenPayloadDTO} object into its JSON string representation.
     *
     * @param refreshTokenPayloadDTO the payload object to convert
     * @return the JSON string representation of the payload
     * @throws JsonException if the conversion fails
     */
    public static String generateRefreshPayload(RefreshTokenPayloadDTO refreshTokenPayloadDTO) {
        try {
            return objectMapper.writeValueAsString(refreshTokenPayloadDTO);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to serialize RefreshTokenPayloadDTO");
        }
    }

    /**
     * Parses a JSON string into a {@link RefreshTokenPayloadDTO} object.
     *
     * @param tokenPayloadJsonString the JSON string representing the payload
     * @return the parsed {@link RefreshTokenPayloadDTO} object
     * @throws JsonException if the parsing fails
     */
    public static RefreshTokenPayloadDTO extractRefreshPayload(String tokenPayloadJsonString) {
        validatePayloadString(tokenPayloadJsonString);

        try {
            return objectMapper.readValue(tokenPayloadJsonString, RefreshTokenPayloadDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to deserialize RefreshTokenPayloadDTO");
        }
    }

    /**
     * Converts the given {@link TempTokenPayloadDTO} object into its JSON string representation.
     *
     * @param tempTokenPayloadDTO the payload object to convert
     * @return the JSON string representation of the payload
     * @throws JsonException if the conversion fails
     */
    public static String generateTempPayload(TempTokenPayloadDTO tempTokenPayloadDTO) {
        try {
            return objectMapper.writeValueAsString(tempTokenPayloadDTO);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to serialize TempTokenPayloadDTO");
        }
    }

    /**
     * Parses a JSON string into a {@link TempTokenPayloadDTO} object.
     *
     * @param tokenPayloadJsonString the JSON string representing the payload
     * @return the parsed {@link TempTokenPayloadDTO} object
     * @throws JsonException if the parsing fails
     */
    public static TempTokenPayloadDTO extractTempPayload(String tokenPayloadJsonString) {
        validatePayloadString(tokenPayloadJsonString);

        try {
            return objectMapper.readValue(tokenPayloadJsonString, TempTokenPayloadDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to deserialize TempTokenPayloadDTO");
        }
    }
}



