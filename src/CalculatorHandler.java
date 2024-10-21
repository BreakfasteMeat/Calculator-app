import java.util.*;

public class CalculatorHandler{
	private CalculatorModel model;
	private CalculatorGUI GUI;
	private Deque<Double> numbers;
	private Deque<Character> operations;
	private Boolean inHighPrecedenceOperation;
	private Double current = 0.0;

	public CalculatorHandler(CalculatorModel model, CalculatorGUI GUI){
		this.model = model;
		this.GUI = GUI;
		this.inHighPrecedenceOperation = false;
		numbers = new LinkedList<>();
		operations = new LinkedList<>();

		for(int i = 0;i < 10;i++){
			int finalI = i;
			GUI.getNumberButtons(i).addActionListener(e -> {
				GUI.typeCharacter(String.valueOf(finalI));
				current *= 10;
				current += finalI;
			});
		}
		GUI.getClearButton().addActionListener(e -> {
			GUI.clearField();
			current = 0.0;
			numbers.clear();
			operations.clear();
		});
		GUI.getEqualsButton().addActionListener(e ->{
//			inHighPrecedenceOperation = false;
//			numbers.add(current);
//			current = calculate();
//			GUI.setDisplayField(String.valueOf(current));
			GUI.setDisplayField("Hello, World!");
		});
		GUI.getAddButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('+'));
			if(inHighPrecedenceOperation){
				numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
				inHighPrecedenceOperation = false;
			}
			System.out.println("Pusing " + current);
			numbers.add(current);
			current = 0.0;
			operations.add('+');
		});
		GUI.getSubtractButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('-'));
			if(inHighPrecedenceOperation){
				numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
				inHighPrecedenceOperation = false;
			}
			numbers.add(current);
			current = 0.0;
			operations.add('-');
		});
		GUI.getMultiplyButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('×'));

			numbers.add(current);
			current = 0.0;
			operations.add('*');
			if(inHighPrecedenceOperation){
				numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
			}
			this.inHighPrecedenceOperation = true;
		});
		GUI.getDivideButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('÷'));

			numbers.add(current);
			current = 0.0;
			operations.add('/');
			if(inHighPrecedenceOperation){
				numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
			}
			this.inHighPrecedenceOperation = true;
		});

	}
	double calculateHighPrecedence(Character c,Double num1, Double num2){
		switch(c){
			case '*':
				model.multiply(num1,num2);
				return model.getResult();
			case '/':
				model.divide(num1,num2);
				return model.getResult();
			case null:
				throw new ArithmeticException("Null operation!");
			default:
				throw new IllegalStateException("Unexpected value: " + c);
		}
	}
	double calculate(){
		double currentValue = numbers.pollFirst();
		double nextValue = 0;
		while(!operations.isEmpty()){
			Character c = operations.pollFirst();
			nextValue = numbers.pollFirst();
			switch(c){
				case '+':
					model.add(currentValue,nextValue);
					currentValue = model.getResult();
					break;
				case '-':
					model.subtract(currentValue,nextValue);
					currentValue = model.getResult();
					break;
				case '*':
					model.multiply(currentValue,nextValue);
					currentValue = model.getResult();
					break;
			}
		}
		return currentValue;
	}

}
