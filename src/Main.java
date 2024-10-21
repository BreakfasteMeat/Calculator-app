//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{
	public static void main(String[] args){
		CalculatorModel calculatorModel = new CalculatorModel();
		CalculatorGUI calculatorGUI = new CalculatorGUI();
		CalculatorHandler calculatorHandler = new CalculatorHandler(calculatorModel,calculatorGUI);
		calculatorGUI.setVisible(true);
	}
}