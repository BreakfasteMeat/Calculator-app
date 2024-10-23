import java.util.*;
import java.math.BigDecimal;

public class CalculatorHandler{
	private CalculatorModel model;
	private CalculatorGUI GUI;
	private Deque<BigDecimal> numbers;
	private Deque<Character> operations;
	private boolean inHighPrecedenceOperation;
	private BigDecimal current = BigDecimal.valueOf(0.0);
	private boolean isFromComputation = false;

	public CalculatorHandler(CalculatorModel model, CalculatorGUI GUI){
		this.model = model;
		this.GUI = GUI;
		this.inHighPrecedenceOperation = false;
		numbers = new LinkedList<>();
		operations = new LinkedList<>();

		for(int i = 0;i < 10;i++){
			int finalI = i;
			GUI.getNumberButtons(i).addActionListener(e -> {
				if(isFromComputation){
					isFromComputation = false;
					GUI.clearField();
				}
				this.GUI.typeCharacter(String.valueOf(finalI));
			});
		}
		GUI.getDecimalPointButton().addActionListener(e -> {
			GUI.typeCharacter(".");
		});
		GUI.getClearButton().addActionListener(e -> {
			GUI.clearField();
			current = new BigDecimal("0.0");
			numbers.clear();
			operations.clear();
		});
		GUI.getEqualsButton().addActionListener(e ->{

			current = parseStringtoEquation(GUI.getDisplayedText());
			GUI.setDisplayField(String.valueOf(current));
			isFromComputation = true;
		});
		GUI.getAddButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('+'));
			isFromComputation = false;
		});
		GUI.getSubtractButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('-'));
			isFromComputation = false;
		});
		GUI.getMultiplyButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('×'));
			isFromComputation = false;
		});
		GUI.getDivideButton().addActionListener(e ->{
			GUI.typeCharacter(String.valueOf('÷'));
			isFromComputation = false;
		});
		GUI.getBackspaceButton().addActionListener(e -> {
			GUI.setDisplayField(GUI.getDisplayedText().substring(0,GUI.getDisplayedText().length() - 1));
		});

		GUI.getPercentButton().addActionListener(e -> {
			GUI.setDisplayField(String.valueOf(Double.parseDouble(GUI.getDisplayedText()) * 0.01));
		});

	}
	BigDecimal calculateHighPrecedence(Character c,BigDecimal num2, BigDecimal num1){
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
	BigDecimal parseStringtoEquation(String equation){
		String number = "0";
		boolean isNotENotation = true;
		for(int i = 0;i < equation.length();i++){
			char c = equation.charAt(i);
			if(Character.isDigit(c) || c == '.' || c == 'E' || !isNotENotation){
				number += c;
				if (i == equation.length() - 1){
					numbers.add(new BigDecimal(number));
					System.out.println(number);
					processHighPrecedence();
				}
				if(c == 'E') isNotENotation = false;
				if((c == '-' || Character.isDigit(c)) && !isNotENotation){
					isNotENotation = true;
				}
			} else if(isOperation(c) && isNotENotation){
				System.out.println(number);
				numbers.add(new BigDecimal(number));
				number = "";
				processOperation(c);
			}
		}
		return calculate();
	}
	BigDecimal calculate(){
		BigDecimal currentValue = numbers.pollFirst();
		BigDecimal nextValue;
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
