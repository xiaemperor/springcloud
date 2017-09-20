package bhz.springcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bhz.springcloud.service.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class )
public class ApplicationTests {
	
	@Autowired
	private IndexService indexService;
	
	@Test
	public void testRetry() throws Exception{
		indexService.call();
	}
	
}
