package hu.ziyan.minesweeper.view.dialogs;

import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.MinesweeperGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AboutDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -4903478112510111407L;
	private JButton closeButton;

	public AboutDialog(MinesweeperGUI gui, boolean modal) {
		super(gui.getWindow(), modal);

		this.setTitle(Labels.about);
		this.setResizable(false);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JPanel mainPanel = createMainPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel buttonPanel = createButtonPanel();
		
		this.add(mainPanel);
		this.add(buttonPanel);
		
		this.pack();
		this.setLocationRelativeTo(gui.getWindow());
		this.setVisible(true);
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		
		closeButton = new JButton(Labels.close);
		closeButton.addActionListener(this);
		
		panel.add(closeButton);
		
		return panel;
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		ImageIcon mineImage = new ImageIcon(getClass().getResource("/res/img/mine-160.png"));
		JLabel picLabel = new JLabel(Labels.game_name, mineImage, JLabel.CENTER);
		picLabel.setHorizontalTextPosition(JButton.CENTER);
		picLabel.setVerticalTextPosition(JButton.BOTTOM);
		picLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel versionLabel = new JLabel("Version: " + "1.00");
		versionLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.add(picLabel);
		panel.add(versionLabel);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton) {
			this.setVisible(false);
		}
	}

}
