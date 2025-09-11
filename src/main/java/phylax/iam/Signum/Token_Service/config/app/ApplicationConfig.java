package phylax.iam.Signum.Token_Service.config.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import phylax.iam.Signum.Token_Service.common.util.logging.LogUtil;
import phylax.iam.Signum.Token_Service.common.util.token.TokenGeneratorUtil;


@Component
public class ApplicationConfig {

    /**
     * Name of the service, injected from the Spring application configuration.
     * <p>
     * Defaults to {@code Signum-Token-Service} if the property
     * {@code spring.application.name} is not explicitly defined.
     * </p>
     */
    @Value("${spring.application.name:Signum-Token-Service}")
    private String serviceName;

    @Value("${token.payloadKeyName:payload}")
    private String tokenPayloadKeyName;

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    @PostConstruct
    public void init() throws Exception {
        LogUtil.init(serviceName);
        TokenGeneratorUtil.init(secretKeyConfig, tokenPayloadKeyName);
    }
}
