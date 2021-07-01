package examples.testingil.characterization;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ContextConfiguration(classes = {CalculatorTestConfiguration.class })
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleCalculatorTests {

	@Autowired
	private MockMvc mockMvc;
	private TestLogger logger;
	

	@Test
	public void CheckDisplayTest() throws Exception
	{
		logger = new TestLogger();
		mockMvc.perform(
				post("/calculator/press")
				.param("key", "1"));
				
		MvcResult result = mockMvc.perform(
				get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();
		
		String responseBody = result.getResponse().getContentAsString();
		Approvals.verify(responseBody);
	}
	
}
