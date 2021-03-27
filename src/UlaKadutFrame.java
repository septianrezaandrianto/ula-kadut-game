import javax.swing.JFrame;

public class UlaKadutFrame extends JFrame {

	
	UlaKadutFrame() {
		this.add(new UlaKadutPanel());
		this.setTitle("Ula Kadut");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
