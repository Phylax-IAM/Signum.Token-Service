package phylax.iam.Signum.Token_Service.jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RevokedTokenCleanUp {

    @Value("${scheduler.revoked-token-cleanup.seconds:300}")
    private Integer intervalInSeconds;


}
