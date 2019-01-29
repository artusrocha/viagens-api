package demo.viagensaapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import demo.viagensapi.config.redis.RedisConfig;

// @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ViagensaapiApplicationTests {

	@Test
	public void contextLoads() {
	}

}

