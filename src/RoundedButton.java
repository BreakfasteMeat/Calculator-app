import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
	private int corner_radius;
	public RoundedButton(String text, int radius){
		super(text);
		this.corner_radius = radius;
		setContentAreaFilled(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Smooth edges

		// Background color
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), corner_radius, corner_radius);

		// Draw the button's label (text)
		super.paintComponent(g);
		g2.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw a rounded border
		g2.setColor(getBackground());
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, corner_radius, corner_radius);
		g2.dispose();
	}

	@Override
	public boolean contains(int x, int y) {
		// Only respond to clicks within the rounded area
		return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), corner_radius, corner_radius).contains(x, y);
	}
}
