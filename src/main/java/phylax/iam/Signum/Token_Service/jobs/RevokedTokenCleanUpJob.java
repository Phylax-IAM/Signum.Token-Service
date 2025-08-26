package phylax.iam.Signum.Token_Service.jobs;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class RevokedTokenCleanUpJob {

    @Value("${scheduler.revoked-token-cleanup.seconds:300}")
    private Integer intervalInSeconds;


}
