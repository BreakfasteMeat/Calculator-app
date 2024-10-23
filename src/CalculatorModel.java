import java.math.*;

public class CalculatorModel{
	private BigDecimal result;

	public BigDecimal getResult(){
		return result;
	}

	public void add(BigDecimal a, BigDecimal b){
		result = a.add(b);
	}
	public void subtract(BigDecimal a, BigDecimal b){
		result = a.subtract(b);
	}
	public void multiply(BigDecimal a, BigDecimal b){
		result = a.multiply(b);
	}
	public void divide(BigDecimal a, BigDecimal b){
		result = a.divide(b, 20,RoundingMode.HALF_UP);
	}
}
