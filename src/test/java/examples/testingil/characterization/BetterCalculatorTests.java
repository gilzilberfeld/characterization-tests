package examples.testingil.characterization;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
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
@AutoConfigureTestDatabase
public class BetterCalculatorTests {

	@Autowired
	private MockMvc mockMvc;
	private TestLogger logger;

	@BeforeEach
	void setup() {
		logger = new TestLogger();
	}
	
	//@Disabled
	@Test
	public void ComplexOperationsTest() throws Exception	{
		reset();
		pressSequence("1+2");
		Approvals.verify(logger.getAll());
	}

	private void pressSequence(String sequence) throws Exception {
		sequence.chars().mapToObj(i -> 
			(char) i).forEach(c -> press (c));
	}

	private void press(char key) {
		String result;
		try {
			mockMvc.perform(
					post("/calculator/press")
					.param("key", Character.toString(key)));
			
			MvcResult response = mockMvc.perform(
					get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();
		
		 		result = response.getResponse().getContentAsString();
		} catch (Exception e) {
			result = "Error";
		}
		
		logger.append(key, result);
	}

	

	private void reset() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "C"));
		logger.append("Reset");
	}
}
