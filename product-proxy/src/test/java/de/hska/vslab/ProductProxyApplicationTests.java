package de.hska.vslab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProductProxyApplication.class)
@WebAppConfiguration
public class ProductProxyApplicationTests {

	@Test
	public void contextLoads() {
	}

}
