package examples.testingil.characterization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorTestConfiguration {
	
	@Bean 
	CalculatorController controller() {
		return new CalculatorController();
	}
	
	@Bean 
	Calculator calculator() {
		return new Calculator();
	}
	
}

