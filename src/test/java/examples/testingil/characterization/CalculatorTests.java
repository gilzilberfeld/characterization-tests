package examples.testingil.characterization;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.approvaltests.Approvals;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@ContextConfiguration(classes = {CalculatorTestConfiguration.class })
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CalculatorTests {

	@Autowired
	private MockMvc mockMvc;
	private StringBuilder log;
	
	//@Disabled
	@Test
	public void GetWithApprovals() throws Exception
	{
		mockMvc.perform(
				post("/calculator/press")
				.param("key", "1"));
				
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();
		
		Approvals.verify(result.getResponse().getContentAsString());

	}
	
	
	@Disabled
	@Test
	public void OperationsWithCalculator() throws Exception	{
		reset();
		pressSequence("1+2=");
		reset();
		pressSequence("1+C");
		Approvals.verify(log.toString());
	}

	@BeforeEach
	public void setup() {
		
		log = new StringBuilder();
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
		
		log.append(addPressResult(key, result));
	}

	private String addPressResult(char key, String display) {
		return "Pressed " + key + ", Display shows: " + display + "\n";
	}

	private void reset() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "C"));
		log.append("Reset\n");
		
	}
}
