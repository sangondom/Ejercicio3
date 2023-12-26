package es.upm.grise.profundizacion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	static Calculator calculator;

	@BeforeAll
	public static void initialize(){
		calculator = new Calculator();
	}

	@BeforeEach
	public void insertData(){
		calculator.add(1);
		calculator.add(2);
	}

	@AfterEach
	public void removeData(){
		calculator.remove(1);
		calculator.remove(2);
	}

	@Test
	public void testMax(){
		assertEquals(2,calculator.max(),"El valor máximo es 2");
	}

	@Test
	public void testMin() {
		assertEquals(1,calculator.min(),"El valor mínimo es 1");
	}

	@Test
	public void testAverage(){
		assertEquals(1.5,calculator.average(),"La media de los valores es 1.5");
	}

	@Test
	public void testStdDev(){
		assertEquals(0.5,calculator.stddev(),"La desviación típica es 0.5");
	}
}
