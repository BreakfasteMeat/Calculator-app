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

			current = parseStringtoEquation(GUI.getDisplayedText());
			GUI.setDisplayField(String.valueOf(current));
		});
		GUI.getAddButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('+'));
		});
		GUI.getSubtractButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('-'));
		});
		GUI.getMultiplyButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('×'));
		});
		GUI.getDivideButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('÷'));
		});

	}
	double calculateHighPrecedence(Character c,Double num2, Double num1){
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
	boolean isOperation(char c){
		return c == '+' || c == '-' || c == '×' || c == '÷';
	}
	void processAddition(){
		processHighPrecedence();
		operations.add('+');
	}
	void processSubtraction(){
		processHighPrecedence();
		operations.add('-');
	}
	void processMultiplication(){
		processHighPrecedence();
		operations.add('*');
		this.inHighPrecedenceOperation = true;
	}
	void processDivision(){
		if(inHighPrecedenceOperation){
			numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
		}
		operations.add('/');
		this.inHighPrecedenceOperation = true;
	}
	void processOperation(char c){
		switch(c){
			case '+':
				processAddition();
				break;
			case '-':
				processSubtraction();
				break;
			case '×':
				processMultiplication();
				break;
			case '÷':
				processDivision();
				break;
			default:
				throw new ArithmeticException("Invalid operation!");
		}
	}
	void processHighPrecedence(){
		if(inHighPrecedenceOperation){
			numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
			inHighPrecedenceOperation = false;
		}
	}
	double parseStringtoEquation(String equation){
		String number = "";
		for(int i = 0;i < equation.length();i++){
			char c = equation.charAt(i);
			if(Character.isDigit(c)){
				number += c;
				if (i == equation.length() - 1){
					numbers.add(Double.parseDouble(number));
					processHighPrecedence();
				}
			} else if(isOperation(c)){
				numbers.add(Double.parseDouble(number));
				number = "";
				processOperation(c);
			}
		}
		return calculate();
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
			}
		}
		return currentValue;
	}

}
