package phylax.iam.Signum.Token_Service.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phylax.iam.Signum.Token_Service.common.cache.InMemoryCache;
import phylax.iam.Signum.Token_Service.common.cache.RedisCache;
import phylax.iam.Signum.Token_Service.common.contract.CacheServiceContract;

import java.util.Optional;

@Service
public class CacheService<K, V> implements CacheServiceContract<K, V> {

    @Autowired
    private RedisCache<K, V> redisCache;

    @Autowired
    private InMemoryCache<K, V> inMemoryCache;

    @Override
    public void saveValue(K key, V value) {
        inMemoryCache.write(key, value);
        redisCache.write(key, value);
    }

    @Override
    public Optional<V> getValue(K key) {
        Optional<V> valueOptional = inMemoryCache.read(key);

        if(valueOptional.isEmpty()) {
            valueOptional = redisCache.read(key);
        }
        return valueOptional;
    }

    @Override
    public void deleteValue(K key) {
        inMemoryCache.delete(key);
        redisCache.delete(key);
    }
}
