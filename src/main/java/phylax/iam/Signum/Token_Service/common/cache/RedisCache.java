package phylax.iam.Signum.Token_Service.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import phylax.iam.Signum.Token_Service.common.contract.PersistableContract;
import phylax.iam.Signum.Token_Service.common.util.logging.LoggerUtil;

import java.util.Optional;

@Component
public final class RedisCache<K, V> implements PersistableContract<K, V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Override
    public Optional<V> read(K key) {
        LoggerUtil.logInfo(logger, "Cache Hit - retrieving data from cache");
        return Optional.ofNullable(
                this.redisTemplate.opsForValue().get(key)
        );
    }

    @Override
    public void write(K key, V value) {
        this.redisTemplate.opsForValue().set(key, value);
        LoggerUtil.logInfo(logger, "Cache Miss - saving data to cache");
    }

    @Override
    public void delete(K key) {
        this.redisTemplate.delete(key);
        LoggerUtil.logInfo(logger, "Cache Evict - data removed from cache");
    }
}
