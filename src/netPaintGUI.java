import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class netPaintGUI extends JFrame {
	public netPaintGUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000,630);
		this.setLocation(0,0);
		layoutGUI();
	}
	
	private JColorChooser colors;
	private JRadioButton  line, rectangel, oval, image;
	private ButtonGroup   shapechoices;
	private JPanel panel;
	private void layoutGUI() {
		panel= new JPanel(null);
		panel.setSize(1000, 630);
		panel.setLocation(0, 0);
		line= new JRadioButton("Line");
		rectangel= new JRadioButton("Rectangel");
		oval= new JRadioButton("Oval");
		image= new JRadioButton("Image");
		line.setSize(70, 40);
		line.setLocation(345, 340);
		rectangel.setSize(100, 40);
		rectangel.setLocation(405, 340);
		oval.setSize(70, 40);
		oval.setLocation(505, 340);
		image.setSize(100, 40);
		image.setLocation(565, 340);
		//add button listeners and actions...

//		shapechoices= new ButtonGroup();
//		shapechoices.add(line);
//		shapechoices.add(rectangel);
//		shapechoices.add(oval);
//		shapechoices.add(image);

		colors = new JColorChooser();
		colors.setSize(1000, 320);
		colors.setLocation(0, 380);
		colors.setVisible(true);
		//panel.add(shapechoices);
		panel.add(colors);
		this.add(line);
		this.add(rectangel);
		this.add(oval);
		this.add(image);

		this.add(panel);
		this.setVisible(true);
		
	}
	public static void main(String [] args){
		new netPaintGUI();
	}
	
}
