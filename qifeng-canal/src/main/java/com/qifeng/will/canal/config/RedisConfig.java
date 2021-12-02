package com.qifeng.will.canal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.defaultExpireTime}")
    private long defaultExpireTime;

    /**
     * 缓存管理器.
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMillis(defaultExpireTime)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * redis模板操作类,类似于jdbcTemplate的一个类; 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活；
     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们 自己的缓存类，比如：RedisStorage类;
     *
     * @param factory
     *            :通过Spring进行注入，参数在application.properties进行配置；
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);

//         key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
//         所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
//         或者JdkSerializationRedisSerializer序列化方式;
         RedisSerializer<String> redisSerializer = new
         StringRedisSerializer();//Long类型不可以会出现异常信息;
         redisTemplate.setKeySerializer(redisSerializer);
         redisTemplate.setHashKeySerializer(redisSerializer);

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }
    
    /**
     * 读取限流脚本
     */
    @Bean
    public DefaultRedisScript<Number> redisluaScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        //这里脚本的路径为path for source root 路径
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luaScript/tokenLua.lua"))); 
        redisScript.setResultType(Number.class);
        return redisScript;
    }

    /**
     * 用于执行限流的脚本
     */
    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    // @Cacheable(cacheNames = {"emp"},keyGenerator = "myKeyGenerator")
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName()+"["+ Arrays.asList(params).toString()+"]";
            }
        };
    }


}
