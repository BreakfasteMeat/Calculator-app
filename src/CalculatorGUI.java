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
	private final JButton magicButton = new JButton("Magic!");
	public CalculatorGUI(){
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,500);



		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 4));

		panel.add(clearButton);
		panel.add(backspaceButton);
		panel.add(percentButton);
		panel.add(divideButton);
		for (int i = 7; i < 10; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			panel.add(numberButtons[i]);
		}
		panel.add(multiplyButton);
		for (int i = 4; i < 7; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			panel.add(numberButtons[i]);
		}
		panel.add(subtractButton);
		for (int i = 1; i < 4; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			panel.add(numberButtons[i]);
		}
		panel.add(addButton);
		panel.add(magicButton);
		numberButtons[0] = new JButton("0");
		panel.add(numberButtons[0]);
		panel.add(decimalPointButton);
		panel.add(equalsButton);



		displayField.setPreferredSize(new Dimension(200,80));
		displayField.setHorizontalAlignment(SwingConstants.RIGHT);
		displayField.setEditable(false);
		displayField.setFont(new Font("SansSerif",Font.BOLD,20));

		this.add(displayField, BorderLayout.NORTH);
		this.add(panel,BorderLayout.CENTER);
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

	public JButton getMagicButton(){
		return magicButton;
	}
}
