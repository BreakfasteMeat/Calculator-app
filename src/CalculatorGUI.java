import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;

public class CalculatorGUI extends JFrame{
	private final JTextField displayField = new JTextField(20);
	private final JTextField equationField = new JTextField(40);
	private final JButton[] numberButtons = new RoundedButton[10];
	private final JButton addButton = new RoundedButton("+",20);
	private final JButton subtractButton = new RoundedButton("-",20);
	private final JButton multiplyButton = new RoundedButton("×",20);
	private final JButton divideButton = new RoundedButton("÷",20);
	private final JButton equalsButton = new RoundedButton("=",20);
	private final JButton clearButton = new RoundedButton("CE",20);
	private final JButton backspaceButton = new RoundedButton("←",20);
	private final JButton percentButton = new RoundedButton("%",20);
	private final JButton decimalPointButton = new RoundedButton(".",20);
	private final JButton openParenthesisButton = new RoundedButton("(",20);
	private final JButton closeParenthesisButton = new RoundedButton(")",20);
	private final JButton sqrtButton = new RoundedButton("√",20);
	private final JPanel main_panel = new JPanel();
	private final JPanel display_panel = new JPanel();
	public CalculatorGUI(){
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,500);


		main_panel.setLayout(new GridLayout(6, 4,7,7));

		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(0,0));
		emptyPanel.setOpaque(false);


		main_panel.add(clearButton);
		main_panel.add(backspaceButton);
		main_panel.add(percentButton);
		main_panel.add(sqrtButton);
		main_panel.add(openParenthesisButton);
		main_panel.add(closeParenthesisButton);
		main_panel.add(emptyPanel);
		main_panel.add(divideButton);

		for (int i = 7; i < 10; i++) {
			numberButtons[i] = new RoundedButton(String.valueOf(i),20);
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(multiplyButton);
		for (int i = 4; i < 7; i++) {
			numberButtons[i] = new RoundedButton(String.valueOf(i),20);
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(subtractButton);
		for (int i = 1; i < 4; i++) {
			numberButtons[i] = new RoundedButton(String.valueOf(i),20);
			main_panel.add(numberButtons[i]);
		}
		main_panel.add(addButton);
		numberButtons[0] = new RoundedButton("0",20);
		main_panel.add(numberButtons[0]);
		main_panel.add(decimalPointButton);
		main_panel.add(equalsButton);


		displayField.setPreferredSize(new Dimension(200,80));
		displayField.setHorizontalAlignment(SwingConstants.RIGHT);
		displayField.setEditable(false);
		displayField.setFont(new Font("Roboto",Font.BOLD,20));

		equationField.setPreferredSize(new Dimension(200,40));
		equationField.setHorizontalAlignment(SwingConstants.RIGHT);
		equationField.setEditable(false);
		equationField.setFont(new Font("Roboto",Font.ITALIC,10));

		display_panel.setLayout(new BorderLayout());
		display_panel.add(equationField, BorderLayout.NORTH);
		display_panel.add(displayField, BorderLayout.SOUTH);
		setGUIColor();
		this.add(display_panel, BorderLayout.NORTH);
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

	public String getEquationFieldText(){ return equationField.getText(); }
	public void setEquationField(String s){ equationField.setText(s); }

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

	private static Color hexColor(String hex){
		return Color.decode(hex);
	}
	private void setGUIColor(){
		main_panel.setBackground(hexColor("#222222"));
		main_panel.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
		setTextFieldStyle(displayField);
		setTextFieldStyle(equationField);
		setAccentButtonStyle(backspaceButton,1);
		setAccentButtonStyle(percentButton,1);
		setAccentButtonStyle(clearButton,1);
		setAccentButtonStyle(closeParenthesisButton,1);
		setAccentButtonStyle(openParenthesisButton,1);
		setAccentButtonStyle(addButton,0);
		setAccentButtonStyle(subtractButton,0);
		setAccentButtonStyle(multiplyButton,0);
		setAccentButtonStyle(divideButton,0);
		for(JButton b : numberButtons){
			setNumberButtonStyle(b);
		}
	}
	private void setTextFieldStyle(JTextField t){
		t.setBackground(hexColor("#444444"));
		t.setForeground(Color.WHITE);
		t.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
		t.setCaretColor(hexColor("#444444"));
	}
	private void setNumberButtonStyle(JButton b){
		b.setFocusPainted(false);
		b.setBackground(hexColor("#333333"));
		b.setForeground(hexColor("#FFFFFF"));
		b.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		b.setFont(new Font("Roboto",Font.PLAIN,20));
		b.getModel().addChangeListener(e -> {
			ButtonModel model = b.getModel();
			if (model.isPressed()) {
				b.setBackground(hexColor("#202020"));
			} else {
				b.setBackground(hexColor("#333333"));
			}
		});
	}
	private void setAccentButtonStyle(JButton b, int accent_num){
		String bg_color, fg_color;
		b.setFocusPainted(false);
		if(accent_num == 1){
			bg_color = "#ea30c0";
			fg_color = "#FFFFFF";
		} else if(accent_num== 2){
			bg_color = "#ea30c0";
			fg_color = "#FFFFFF";
		} else {
			bg_color = "#30EA5A";
			fg_color = "#000000";
		}
		b.setBackground(hexColor(bg_color));
		b.setForeground(hexColor(fg_color));
		b.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		b.setFont(new Font("Roboto",Font.PLAIN,20));
		b.getModel().addChangeListener(e -> {
			ButtonModel model = b.getModel();
			if (model.isPressed()) {
				b.setBackground(hexColor("#202020"));
				b.setForeground(hexColor("#FFFFFF"));
			} else {
				b.setBackground(hexColor(bg_color));
				b.setForeground(hexColor(fg_color));
			}
		});
	}

}
