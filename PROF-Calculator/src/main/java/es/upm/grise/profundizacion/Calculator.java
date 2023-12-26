package es.upm.grise.profundizacion;

import java.util.Vector;
import java.lang.Double;

public class Calculator {

	public Vector <Double> numbers;
	
	public Calculator(){
		numbers = new Vector<Double>();
	}

	// Public methods
	//
	public void add(double d) {
		numbers.add(d);
	}
	
	public void remove(double number) {
		numbers.remove(numbers.lastIndexOf(number));
	}
	
	public double max() {
		double max = Double.MIN_VALUE;
		
		for(double number : numbers)
			if(number > max)
				max = number;
		
		return max;
	}
	
	public double min() {
		double min = Double.MAX_VALUE;
		
		for(double number : numbers)
			if(number < min)
				min = number;
		
		return min;
	}
	
	public double average() {
		return sum() / count();
	}
	
	public double stddev() {
		return sum_respect_average() / (count() - 1);
	}
	
	// Private methods
	//
	private double sum() {
		double sum = 0;
		
		for(double number : numbers)
			sum += number;
		
		return sum;
	}
	
	private double sum_respect_average() {
		double average = average();
		double sum_respect_average = 0;
		
		for(double number : numbers)
			sum_respect_average += (average - number) * (average - number);
		
		return sum_respect_average;
	}
	
	private float count() {
		return numbers.size();
	}
	
}