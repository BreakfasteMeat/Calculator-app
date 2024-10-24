import java.awt.event.ActionListener;
import java.util.*;
import java.math.BigDecimal;

public class CalculatorHandler{
	private CalculatorModel model;
	private CalculatorGUI GUI;
	private boolean inHighPrecedenceOperation;
	private BigDecimal current = BigDecimal.valueOf(0.0);
	private boolean isFromComputation = false;
	private int parenthesisJump = -1;

	/*
	* This function will get the number in the current equation given
	* How many places back
	*   example:
	*   Equation is "20+60*90-25"
	*   if getNumber(0) is called, it will get the number
	*   0 places back which is 25
	*
	*   if getNumber(2) is called, it will get the number
	*   2 places back which is 60
	* */
	private String getNumber(int positions_back){
		String result = "";
		String equation = GUI.getDisplayedText();
		int index = equation.length();
		for(int i = 0;i <= positions_back;i++){
			result = "";
			try {
				do {
					index--;
					if (!isOperation(equation.charAt(index))) result = equation.charAt(index) + result;
					System.out.println("Adding " + equation.charAt(index) + " to result");

				} while (!isOperation(equation.charAt(index)) && index > 0);
			} catch (IndexOutOfBoundsException exc){
				return "1";
			}
		}
		System.out.println(result);
		return result;
	}

	public CalculatorHandler(CalculatorModel model, CalculatorGUI GUI){
		this.model = model;
		this.GUI = GUI;
		this.inHighPrecedenceOperation = false;

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
		GUI.getDecimalPointButton().addActionListener(e -> GUI.typeCharacter("."));
		GUI.getOpenParenthesisButton().addActionListener(e -> GUI.typeCharacter("("));
		GUI.getCloseParenthesisButton().addActionListener(e -> GUI.typeCharacter(")"));
		GUI.getClearButton().addActionListener(e -> {
			GUI.clearField();
			current = new BigDecimal("0.0");
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
		GUI.getBackspaceButton().addActionListener(e -> GUI.setDisplayField(GUI.getDisplayedText().substring(0,GUI.getDisplayedText().length() - 1)));

		GUI.getPercentButton().addActionListener(e -> {

			String percentage = "", percentage_of = "";
			percentage = getNumber(0);
			percentage_of = getNumber(1);
			GUI.setDisplayField(GUI.getDisplayedText().substring(0,GUI.getDisplayedText().length() - percentage.length()));
			BigDecimal percentage_value = new BigDecimal(percentage);
			BigDecimal percentage_of_value = new BigDecimal(percentage_of);
			System.out.println("Getting " + percentage + "% of " + percentage_of);
			System.out.println("Getting " + percentage_value + "% of " + percentage_of_value);
			BigDecimal result = percentage_value.multiply(percentage_of_value.multiply(new BigDecimal("0.01")));
			GUI.setDisplayField(GUI.getDisplayedText() + result);
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
	void processAddition(Deque<BigDecimal> numbers, Deque<Character> operations){
		processHighPrecedence(numbers, operations);
		operations.add('+');
	}
	void processSubtraction(Deque<BigDecimal> numbers, Deque<Character> operations){
		processHighPrecedence(numbers, operations);
		operations.add('-');
	}
	void processMultiplication(Deque<BigDecimal> numbers, Deque<Character> operations){
		processHighPrecedence(numbers, operations);
		operations.add('*');
		this.inHighPrecedenceOperation = true;
	}
	void processDivision(Deque<BigDecimal> numbers, Deque<Character> operations){
		if(inHighPrecedenceOperation){
			numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
		}
		operations.add('/');
		this.inHighPrecedenceOperation = true;
	}
	void processOperation(char c,Deque<BigDecimal> numbers, Deque<Character> operations){
		switch(c){
			case '+':
				processAddition(numbers, operations);
				break;
			case '-':
				processSubtraction(numbers, operations);
				break;
			case '×':
				processMultiplication(numbers, operations);
				break;
			case '÷':
				processDivision(numbers, operations);
				break;
			default:
				throw new ArithmeticException("Invalid operation!");
		}
	}
	void processHighPrecedence(Deque<BigDecimal> numbers, Deque<Character> operations){
		if(inHighPrecedenceOperation){
			numbers.add(calculateHighPrecedence(operations.pollLast(),numbers.pollLast(),numbers.pollLast()));
			inHighPrecedenceOperation = false;
		}
	}
	BigDecimal parseStringtoEquation(String equation){
		Deque<BigDecimal> numbers = new LinkedList<>();
		Deque<Character> operations = new LinkedList<>();
		inHighPrecedenceOperation = false;
		String number = "0";
		boolean isNotENotation = true;
		for(int i = 0;i < equation.length();i++){
			if(parenthesisJump != -1){
				System.out.println("Jumping to " + parenthesisJump + i);
				i += parenthesisJump - 1;
				parenthesisJump = -1;
				break;
			}
			char c = equation.charAt(i);
			if(Character.isDigit(c) || c == '.' || c == 'E' || !isNotENotation){
				number += c;
				if (i == equation.length() - 1){
					numbers.add(new BigDecimal(number));
					System.out.println(number);
					processHighPrecedence(numbers, operations);
				}
				if(c == 'E') isNotENotation = false;
				if((c == '-' || Character.isDigit(c)) && !isNotENotation){
					isNotENotation = true;
				}
			} else if(isOperation(c) && isNotENotation){
				System.out.println(number);
				numbers.add(new BigDecimal(number));
				number = "";
				processOperation(c,numbers, operations);
			} else if(c == '('){
				//TODO Parenthesis logic
				System.out.println("Passing " + (equation.substring(i+1)));
				numbers.add(new BigDecimal(String.valueOf(parseStringtoEquation(equation.substring(++i)))));
			} else if(c == ')'){
				parenthesisJump = equation.length() - 1;
				System.out.println("Adding " + number);
				numbers.add(new BigDecimal(number));
				System.out.println("Returning " + calculate(numbers, operations));
				return calculate(numbers, operations);
			}
		}
		return calculate(numbers, operations);
	}
	BigDecimal calculate(Deque<BigDecimal> numbers, Deque<Character> operations){
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
