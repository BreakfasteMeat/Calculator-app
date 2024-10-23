import javax.swing.*;
import java.awt.*;

public class CalculatorGUI extends JFrame{
	private final JTextField displayField = new JTextField(20);
	private final JButton[] numberButtons = new JButton[10];
	private final JButton addButton = new JButton("+");
	private final JButton subtractButton = new JButton("-");
	private final JButton multiplyButton = new JButton("×");
	private final JButton divideButton = new JButton("÷");
	private final JButton equalsButton = new JButton("=");
	private final JButton clearButton = new JButton("CE");
	private final JButton backspaceButton = new JButton("⌫");
	private final JButton percentButton = new JButton("%");
	private final JButton decimalPointButton = new JButton(".");
	private final JButton openParenthesisButton = new JButton("(");
	private final JButton closeParenthesisButton = new JButton(")");
	public CalculatorGUI(){
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,500);



		JPanel main_panel = new JPanel();
		JPanel span_panel = new JPanel();
		main_panel.setLayout(new GridLayout(6, 4));
		span_panel.setLayout(new BorderLayout());
		span_panel.add(equalsButton,BorderLayout.CENTER);

		main_panel.add(clearButton);
		main_panel.add(backspaceButton);
		main_panel.add(percentButton);
		main_panel.add(divideButton);
		for (int i = 7; i < 10; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(multiplyButton);
		for (int i = 4; i < 7; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(subtractButton);
		for (int i = 1; i < 4; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(addButton);
		numberButtons[0] = new JButton("0");
		main_panel.add(numberButtons[0]);
		main_panel.add(decimalPointButton);
		main_panel.add(span_panel);
		main_panel.add(openParenthesisButton);
		main_panel.add(closeParenthesisButton);


		displayField.setPreferredSize(new Dimension(200,80));
		displayField.setHorizontalAlignment(SwingConstants.RIGHT);
		displayField.setEditable(false);
		displayField.setFont(new Font("SansSerif",Font.BOLD,20));

		this.add(displayField, BorderLayout.NORTH);
		this.add(main_panel);
	}
	public String getDisplayedText(){
		return displayField.getText();
	}
	public void typeCharacter(String input){
		displayField.setText(displayField.getText() + input);
	}
	public void clearField(){
		displayField.setText("");
	}
	public void setDisplayField(String s){
		displayField.setText(s);
	}

	public JButton getNumberButtons(int index){
		return numberButtons[index];
	}

	public JButton getAddButton(){
		return addButton;
	}

	public JButton getSubtractButton(){
		return subtractButton;
	}

	public JButton getMultiplyButton(){
		return multiplyButton;
	}

	public JButton getDivideButton(){
		return divideButton;
	}

	public JButton getEqualsButton(){
		return equalsButton;
	}

	public JButton getClearButton(){
		return clearButton;
	}

	public JButton getBackspaceButton(){
		return backspaceButton;
	}

	public JButton getPercentButton(){
		return percentButton;
	}

	public JButton getDecimalPointButton(){
		return decimalPointButton;
	}

	public JButton getCloseParenthesisButton(){
		return closeParenthesisButton;
	}

	public JButton getOpenParenthesisButton(){
		return openParenthesisButton;
	}
}
