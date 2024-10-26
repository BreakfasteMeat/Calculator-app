import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.Tree;

import java.awt.event.ActionListener;
import java.util.*;
import java.math.BigDecimal;

public class CalculatorHandler{
	private CalculatorModel model;
	private CalculatorGUI GUI;
	private BigDecimal current = BigDecimal.valueOf(0.0);
	private boolean isFromComputation = false;

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

				} while (!isOperation(equation.charAt(index)) && index > 0);
			} catch (IndexOutOfBoundsException exc){
				return "1";
			}
		}
		return result;
	}

	public CalculatorHandler(CalculatorModel model, CalculatorGUI GUI){
		this.model = model;
		this.GUI = GUI;

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

			System.out.println(GUI.getDisplayedText());
			current = parseStringtoEquation(GUI.getDisplayedText());
			GUI.setEquationField(GUI.getDisplayedText() + "=");
			GUI.setDisplayField(String.valueOf(current));
			isFromComputation = true;
			int haha = new Random().nextInt(0,11);
			if(haha == 5){
				GUI.setDisplayField("Hello, World!");
			}
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
			BigDecimal result = percentage_value.multiply(percentage_of_value.multiply(new BigDecimal("0.01")));
			GUI.setDisplayField(GUI.getDisplayedText() + result);
		});

	}

	boolean isOperation(char c){
		return c == '+' || c == '-' || c == '×' || c == '÷';
	}

	void processAddition(Deque<BigDecimal> numbers, Deque<Character> operations){
		operations.add('+');
	}
	void processSubtraction(Deque<BigDecimal> numbers, Deque<Character> operations){
		operations.add('-');
	}
	void processMultiplication(Deque<BigDecimal> numbers, Deque<Character> operations){
		operations.add('*');
	}
	void processDivision(Deque<BigDecimal> numbers, Deque<Character> operations){
		operations.add('/');
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

	BigDecimal parseStringtoEquation(String equation){
		System.out.println("~~~~");
		Deque<BigDecimal> numbers = new LinkedList<>();
		Deque<Character> operations = new LinkedList<>();
		String number = "";
		boolean isNotENotation = true;
		for(int i = 0;i < equation.length();i++){
			System.out.println("Im at index " + i);

			char c = equation.charAt(i);
			if(Character.isDigit(c) || c == '.' || c == 'E' || !isNotENotation){
				number += c;
				if (i == equation.length() - 1){
					numbers.add(new BigDecimal(number));
				}
				if(c == 'E') isNotENotation = false;
				if((c == '-' || Character.isDigit(c)) && !isNotENotation){
					isNotENotation = true;
				}
			} else if(isOperation(c) && isNotENotation){

				if(equation.charAt(i-1) != ')')numbers.add(new BigDecimal(number));

				number = "";
				processOperation(c,numbers, operations);
			} else if(c == '('){
				Stack<Character> parentheses = new Stack<>();
				parentheses.push('(');
				int j = 0;
				for(j = i + 1;!parentheses.isEmpty();j++){
					if(equation.charAt(j) == '('){
						parentheses.push(equation.charAt(j));
					} else if(equation.charAt(j) == ')'){
						parentheses.pop();
					}
				}
				j--;
				System.out.println(i + " : " + j);
				numbers.add(parseStringtoEquation(equation.substring(i+1,j)));
				i=j;
			}
		}
		return calculate(numbers, operations);
	}
	BigDecimal calculate(Deque<BigDecimal> numbers, Deque<Character> operations){
		Deque<BigDecimal> secondary_numbers = new LinkedList<>();
		Deque<Character> secondary_operations = new LinkedList<>();
		secondary_numbers.add(numbers.pollFirst());
		while(!operations.isEmpty()){

			if(operations.getFirst() == '+' || operations.getFirst() == '-'){
				secondary_numbers.add(numbers.pollFirst());
				secondary_operations.add(operations.pollFirst());
			} else {
				if(operations.getFirst() == '*'){
					model.multiply(secondary_numbers.pollLast(), numbers.pollFirst());
					secondary_numbers.addLast((model.getResult()));
				} else if(operations.getFirst() == '/'){
					model.divide(secondary_numbers.pollLast(), numbers.pollFirst());
					secondary_numbers.addLast((model.getResult()));
				}
				operations.pollFirst();
			}


		}
		if(!numbers.isEmpty())secondary_numbers.add(numbers.pollFirst());

		System.out.println(secondary_numbers);
		System.out.println(secondary_operations);
		
		while(!secondary_operations.isEmpty()){
			if(secondary_operations.getFirst() == '+'){
				model.add(secondary_numbers.pollFirst(),secondary_numbers.pollFirst());
				secondary_numbers.add(model.getResult());
			} else if(secondary_operations.getFirst() == '-'){
				model.subtract(secondary_numbers.pollFirst(),secondary_numbers.pollFirst());
				secondary_numbers.add(model.getResult());
			}
			secondary_operations.pollFirst();
		}

		return secondary_numbers.pollFirst();
	}

}
