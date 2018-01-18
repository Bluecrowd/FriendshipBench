package nl.friendshipbench.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFalse()
	{
		assertThat("test").contains("est");
	}

	@Test
	public void testTrue()
	{
		assertThat("test").contains("es");
	}

}
