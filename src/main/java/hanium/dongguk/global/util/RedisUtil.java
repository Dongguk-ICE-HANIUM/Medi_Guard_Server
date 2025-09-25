package hanium.dongguk.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.exception.GlobalErrorCode;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T> String generateAndStoreCode(T data, Duration expiration){
        String code = generateAuthCode();
        redisTemplate.opsForValue().set("code" + code, toJson(data), expiration);
        return code;
    }

    public <T> T getAndValidateCode(String code, Class<T> clazz){
        String key = "code" + code;
        String json = redisTemplate.opsForValue().get(key);
        if(json == null){
            throw CommonException.type(GlobalErrorCode.INVALID_OR_EXPIRED_CODE);
        }
        redisTemplate.delete(key);
        return fromJson(json, clazz);
    }

    private String generateAuthCode(){
        SecureRandom secureRandom = new SecureRandom();
        String code;

        int attempts = 0;
        final int MAX_ATTEMPTS = 10;

        do{
            code = String.format("%08d", secureRandom.nextInt(100000000));
            attempts++;

            if(attempts > MAX_ATTEMPTS){
                throw CommonException.type(ScheduleErrorCode.FAILED_GENERATE_AUTH_CODE);
            }

        } while(redisTemplate.hasKey("code" + code));

        return code;
    }

    private String toJson(Object object){
        try{
            return objectMapper.writeValueAsString(object);
        }catch (Exception e){
            throw CommonException.type(GlobalErrorCode.FAILED_CODE_SERIALIZATION);
        }
    }

    private <T> T fromJson(String json, Class<T> clazz){
        try{
            return objectMapper.readValue(json, clazz);
        }catch (Exception e){
            throw CommonException.type(GlobalErrorCode.FAILED_CODE_DESERIALIZATION);
        }
    }
}


