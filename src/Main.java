/*
* Járai Dániel
* JADUAAT.SZE
*/
import javax.swing.JFrame;
import javax.swing.UIManager;
import hu.ziyan.minesweeper.NehezsegValasztas;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Nem sikerült alkalmazkodni a rendszer kinézetéhez.");
		}
		JFrame nehezsegValasztas = new NehezsegValasztas();
		nehezsegValasztas.setVisible(true);
	}
}
