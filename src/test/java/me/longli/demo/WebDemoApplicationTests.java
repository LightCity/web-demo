package me.longli.demo;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebDemoApplicationTests {

	@Autowired
	private RedissonClient redissonClient;

	@Test
	public void contextLoads() {
		RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("bloomFilter");
		bloomFilter.tryInit(100_0000L, 0.03f);
		bloomFilter.add("longli");
		System.out.println(bloomFilter.contains("juncheng"));
		System.out.println(bloomFilter.contains("longli"));
	}

}
