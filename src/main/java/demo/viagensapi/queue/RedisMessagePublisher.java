package demo.viagensapi.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public RedisMessagePublisher() {
    }

    public RedisMessagePublisher(final RedisTemplate<String, Object> redisTemplate2, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate2;
        this.topic = topic;
    }

    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
