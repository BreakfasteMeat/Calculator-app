import java.util.Stack;

public class CalculatorHandler{
	private CalculatorModel model;
	private CalculatorGUI GUI;
	private Stack<Double> numbers;
	private Stack<Character> operations;
	private Double current = 0.0;

	public CalculatorHandler(CalculatorModel model, CalculatorGUI GUI){
		this.model = model;
		this.GUI = GUI;
		numbers = new Stack<>();
		operations = new Stack<>();

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
		});

	}
	void calculate(){

	}

}
