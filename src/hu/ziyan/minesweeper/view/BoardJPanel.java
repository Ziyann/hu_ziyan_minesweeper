package hu.ziyan.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardJPanel extends JPanel {

	private static final long serialVersionUID = -3378369239204225291L;

	protected int ido = 0;
	protected int maradekSegitseg = 3;
	protected int maradekMezo;
	protected Timer szamlalo;
	protected char[][] mezo;
	protected boolean elsoLepes = true;
	protected boolean aknaraLepes = false;
	protected boolean uresMezo = false;
	protected JLabel idomero = new JLabel(Labels.time_label + ": 0");
	protected JButtonField fieldButtons[][];
	protected JLabel segitsegSzamlalo = new JLabel(Labels.remaining_help_label + ": 3");

	MinesweeperGUI gui;

	public BoardJPanel(MinesweeperGUI gui, final int sorokSzama, final int oszlopokSzama, final int aknakSzama,
			final String nehezsegiSzint) {
		super();
		this.gui = gui;
		createBoard(sorokSzama, oszlopokSzama, aknakSzama, nehezsegiSzint);
	}

	private void createBoard(final int sorokSzama, final int oszlopokSzama, final int aknakSzama,
			final String nehezsegiSzint) {
		JPanel foAblak = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		mezo = mezoLetrehozasa(sorokSzama, oszlopokSzama, aknakSzama);
		maradekMezo = (sorokSzama * oszlopokSzama) - aknakSzama;

		/*
		 * make buttons and set event listeners for them
		 */
		fieldButtons = new JButtonField[sorokSzama][oszlopokSzama];
		for (int i = 0; i < sorokSzama; i++) {
			for (int j = 0; j < oszlopokSzama; j++) {
				fieldButtons[i][j] = new JButtonField();

				final int aktualisSor = i;
				final int aktualisOszlop = j;

				fieldButtons[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent kattintas) {
						if (kattintas.isShiftDown()) {
							segitsegKerese(aktualisSor, aktualisOszlop, sorokSzama, oszlopokSzama, nehezsegiSzint);
						} else if (kattintas.isMetaDown() && fieldButtons[aktualisSor][aktualisOszlop].isEnabled()) {
							fieldButtons[aktualisSor][aktualisOszlop].placeFlag();
						} else if (fieldButtons[aktualisSor][aktualisOszlop].getText().equals("P")) {
							fieldButtons[aktualisSor][aktualisOszlop].removeFlag();
						} else if (fieldButtons[aktualisSor][aktualisOszlop].isEnabled()) {
							while (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals("x") && elsoLepes) {
								mezo = mezoLetrehozasa(sorokSzama, oszlopokSzama, aknakSzama);
							}
							mezoFelfedese(aktualisSor, aktualisOszlop);
							// idomero.requestFocus(); // workaround to avoid
							// selection of button

							aknaraLepes = false;
							uresMezo = false;
							if (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals("x")) {
								aknaraLepes = true;
							} else if (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals(" ")) {
								uresMezo = true;
							}

							if (!aknaraLepes) {
								if (elsoLepes) { // start timer
									ActionListener listener = new ActionListener() {
										public void actionPerformed(ActionEvent event) {
											idomero.setText(Labels.time_label + ": " + Integer.toString(++ido));
										}
									};
									szamlalo = new Timer(1000, listener);
									szamlalo.start();
									elsoLepes = false;
								}
								if (uresMezo) {
									uresMezokFelfedese(sorokSzama, oszlopokSzama, aktualisSor, aktualisOszlop);
								}
								if (maradekMezo == 0) {
									szamlalo.stop();
									JFrame eredmenyek = new Eredmenyek(nehezsegiSzint, ido);
									eredmenyek.setVisible(true);
									setVisible(false);
								}
							}

							if (aknaraLepes) {
								szamlalo.stop();
								aknakFelfedese(sorokSzama, oszlopokSzama);
								felugroLehetosegek();
							}
						}
					}
				}); // addmouselistener end

				c.gridx = j;
				c.gridy = i;
				foAblak.add(fieldButtons[i][j], c);
			}
		} // for loop end

		c.insets = new Insets(0, 0, 0, 2);
		c.gridx = oszlopokSzama - 6;
		c.gridy = sorokSzama;
		c.gridwidth = 6;
		c.anchor = GridBagConstraints.LINE_END;
		segitsegSzamlalo.setFont(segitsegSzamlalo.getFont().deriveFont(15.0f));
		foAblak.add(segitsegSzamlalo, c);

		c.gridx = oszlopokSzama - 3;
		c.gridy++;
		c.gridwidth = 4;
		idomero.setFont(idomero.getFont().deriveFont(15.0f));
		foAblak.add(idomero, c);

		add(foAblak, BorderLayout.CENTER);
	}

	private char[][] mezoLetrehozasa(int sorokSzama, int oszlopokSzama, int aknakSzama) {
		char[][] mezo = new char[sorokSzama][oszlopokSzama];

		for (int sor = 0; sor < sorokSzama; sor++) {
			for (int oszlop = 0; oszlop < oszlopokSzama; oszlop++) {
				mezo[sor][oszlop] = ' ';
			}
		}

		for (int i = 0; i < aknakSzama; i++) {
			Random veletlen = new Random();
			while (true) {
				int veletlenSor = veletlen.nextInt(sorokSzama);
				int veletlenOszlop = veletlen.nextInt(oszlopokSzama);
				if (mezo[veletlenSor][veletlenOszlop] != 'x') {
					mezo[veletlenSor][veletlenOszlop] = 'x';
					break;
				} else {
					continue;
				}
			}
		}

		for (int sor = 0; sor < sorokSzama; sor++) {
			for (int oszlop = 0; oszlop < oszlopokSzama; oszlop++) {
				if (mezo[sor][oszlop] == 'x') { // ha akna, tovább lép a
												// ciklus
					continue;
				}
				byte kornyezoAknakSzama = 0;
				if (sor < sorokSzama - 1 && oszlop < oszlopokSzama - 1 && mezo[sor + 1][oszlop + 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (sor < sorokSzama - 1 && mezo[sor + 1][oszlop] == 'x') {
					kornyezoAknakSzama++;
				}
				if (oszlop > 0 && sor < sorokSzama - 1 && mezo[sor + 1][oszlop - 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (sor > 0 && oszlop > 0 && mezo[sor - 1][oszlop - 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (sor > 0 && oszlop < oszlopokSzama - 1 && mezo[sor - 1][oszlop + 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (sor > 0 && mezo[sor - 1][oszlop] == 'x') {
					kornyezoAknakSzama++;
				}
				if (oszlop < oszlopokSzama - 1 && mezo[sor][oszlop + 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (oszlop > 0 && mezo[sor][oszlop - 1] == 'x') {
					kornyezoAknakSzama++;
				}
				if (kornyezoAknakSzama == 0) {
					mezo[sor][oszlop] = ' ';
				} else {
					mezo[sor][oszlop] = Character.forDigit(kornyezoAknakSzama, 10);
				}
			}
		}
		return mezo;
	}

	private void segitsegKerese(int sor, int oszlop, int sorokSzama, int oszlopokSzama, String nehezsegiSzint) {
		if (maradekSegitseg > 0) {
			if (elsoLepes) { // start timer
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						idomero.setText(Labels.time_label + ": " + Integer.toString(++ido));
					}
				};
				szamlalo = new Timer(1000, listener);
				szamlalo.start();
				elsoLepes = false;
			}
			ido += 30;
			segitsegSzamlalo.setText(Labels.remaining_help_label + ": " + --maradekSegitseg);
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (sor + i < sorokSzama && sor + i >= 0 && oszlop + j < oszlopokSzama && oszlop + j >= 0) {
						if (Character.toString(mezo[sor + i][oszlop + j]).equals("x")) {
							fieldButtons[sor + i][oszlop + j].placeFlag();
						} else {
							mezoFelfedese(sor + i, oszlop + j);
						}
					}
				}
			}
			if (maradekMezo == 0) {
				szamlalo.stop();
				JFrame eredmenyek = new Eredmenyek(nehezsegiSzint, ido);
				eredmenyek.setVisible(true);
				setVisible(false);
			}
		}
	}

	private void aknakFelfedese(final int sorokSzama, final int oszlopokSzama) {
		for (int i = 0; i < sorokSzama; i++) {
			for (int j = 0; j < oszlopokSzama; j++) {
				if (fieldButtons[i][j].getText().equals("P") && Character.toString(mezo[i][j]).equals("x")) {
					fieldButtons[i][j].setForeground(Color.RED);
					fieldButtons[i][j].setEnabled(false);
				} else if (Character.toString(mezo[i][j]).equals("x")) {
					fieldButtons[i][j].setText("X");
					fieldButtons[i][j].setForeground(Color.RED);
					fieldButtons[i][j].setEnabled(false);

				}
			}
		}
	}

	private void uresMezokFelfedese(final int sorokSzama, final int oszlopokSzama, final int aktualisSor,
			final int aktualisOszlop) {
		int tempOszlop = aktualisOszlop;
		int tempSor = aktualisSor;
		/*
		 * lefelé indul
		 */
		while (++tempSor < sorokSzama && Character.toString(mezo[tempSor][aktualisOszlop]).equals(" ")
				&& fieldButtons[tempSor][aktualisOszlop].isEnabled()) {
			/*
			 * felfedi az aktuális sort (++sor amég üres) addig megy lefelé,
			 * amég üres a mező
			 */
			mezoFelfedese(tempSor, aktualisOszlop);
			/*
			 * közben minden üres sorban elindul jobbra
			 */
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) {
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop < oszlopokSzama) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
			/*
			 * majd balra
			 */
			while (true) {
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) { // balra
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop >= 0) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
		}
		/*
		 * tempSor az utolsó üres sor utáni sor pozícióján áll az oszlop
		 * pedig az eredeti
		 */
		if (tempSor < sorokSzama) {
			mezoFelfedese(tempSor, aktualisOszlop);
		}
		tempOszlop = aktualisOszlop;
		tempSor = aktualisSor;
		/*
		 * felfelé indul
		 */
		while (--tempSor >= 0 && Character.toString(mezo[tempSor][aktualisOszlop]).equals(" ")
				&& fieldButtons[tempSor][aktualisOszlop].isEnabled()) {
			mezoFelfedese(tempSor, aktualisOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) { // jobbra
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop < oszlopokSzama) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
			while (true) {
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) { // balra
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop >= 0) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
		}
		if (tempSor >= 0) {
			mezoFelfedese(tempSor, aktualisOszlop);
		}
		tempOszlop = aktualisOszlop;
		tempSor = aktualisSor;
		/*
		 * jobbra indul
		 */
		while (++tempOszlop < oszlopokSzama && Character.toString(mezo[aktualisSor][tempOszlop]).equals(" ")
				&& fieldButtons[aktualisSor][tempOszlop].isEnabled()) {
			mezoFelfedese(aktualisSor, tempOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) {
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop < oszlopokSzama) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
			while (true) {
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) {
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop >= 0) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
		}
		if (tempOszlop < oszlopokSzama) {
			mezoFelfedese(aktualisSor, tempOszlop);
		}
		tempOszlop = aktualisOszlop;
		tempSor = aktualisSor;
		/*
		 * balra indul
		 */
		while (--tempOszlop >= 0 && Character.toString(mezo[aktualisSor][tempOszlop]).equals(" ")
				&& fieldButtons[aktualisSor][tempOszlop].isEnabled()) {
			mezoFelfedese(aktualisSor, tempOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) {
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop < oszlopokSzama) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
			while (true) {
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& fieldButtons[tempSor][tempOszlop].isEnabled()) {
					mezoFelfedese(tempSor, tempOszlop);
					uresMezokFelfedese(sorokSzama, oszlopokSzama, tempSor, tempOszlop);
				}
				if (tempOszlop >= 0) {
					mezoFelfedese(tempSor, tempOszlop);
				}
				tempOszlop = aktualisOszlop;
				break;
			}
		}
		if (tempOszlop >= 0) {
			mezoFelfedese(aktualisSor, tempOszlop);
		}
		tempOszlop = aktualisOszlop;
		tempSor = aktualisSor;
	}

	private void felugroLehetosegek() {
		Object[] gombok = { Labels.new_game, Labels.exit_label };
		int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.gui_title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, gombok, gombok[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			setVisible(false);
			gui.showBoardPanel(9, 9, 10, Labels.beginner); // TODO fix
		} else if (valasztas == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	private void mezoFelfedese(final int aktualisSor, final int aktualisOszlop) {
		if (fieldButtons[aktualisSor][aktualisOszlop].isEnabled()) {
			maradekMezo--;
			fieldButtons[aktualisSor][aktualisOszlop].reveal(Character
					.getNumericValue((mezo[aktualisSor][aktualisOszlop])));
		}
	}

}
