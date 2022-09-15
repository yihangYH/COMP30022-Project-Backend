package IT.Project.IT;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ItApplicationTests {
	
	Calculator underTest = new Calculator();

	@Test
	void itShouldAddNumbers() {
		// Given
		int number1 = 20;
		int number2 = 30;
		
		// when
		int result = underTest.add(number1, number2);
		
		// then
		assertThat(result).isEqualTo(50);
	}

	class Calculator{
		int add(int a, int b){return a + b;}
	}

}
