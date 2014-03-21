package hu.ziyan.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tabla extends JFrame {
	private static final long serialVersionUID = -4771689322191419156L;

	protected int ido = 0;
	protected int maradekSegitseg = 3;
	protected int maradekMezo;
	protected Timer szamlalo;
	protected char[][] mezo;
	protected boolean elsoLepes = true;
	protected boolean aknaraLepes = false;
	protected boolean uresMezo = false;
	protected JLabel idomero = new JLabel("Idő: 0");
	protected JButton mezoGombok[][];
	protected JLabel segitsegSzamlalo = new JLabel("Maradék segítség: 3");

	public Tabla(final int sorokSzama, final int oszlopokSzama, final int aknakSzama, final String nehezsegiSzint) {
		setLocation(100, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Aknakereső");

		JPanel foAblak = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		mezo = mezoLetrehozasa(sorokSzama, oszlopokSzama, aknakSzama);
		maradekMezo = (sorokSzama * oszlopokSzama) - aknakSzama;

		/*
		 * feltöltés gombokkal és eseménykezelők beállítása
		 */
		mezoGombok = new JButton[sorokSzama][oszlopokSzama];
		for (int i = 0; i < sorokSzama; i++) {
			for (int j = 0; j < oszlopokSzama; j++) {
				mezoGombok[i][j] = new JButton("");
				mezoGombok[i][j].setPreferredSize(new Dimension(25, 25));
				mezoGombok[i][j].setMargin(new Insets(1, 1, 1, 1));
				mezoGombok[i][j].setFont(mezoGombok[i][j].getFont().deriveFont(20.0f));

				final int aktualisSor = i;
				final int aktualisOszlop = j;

				mezoGombok[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent kattintas) {
						if (kattintas.isShiftDown()) {
							segitsegKerese(aktualisSor, aktualisOszlop, sorokSzama, oszlopokSzama, nehezsegiSzint);
						} else if (kattintas.isMetaDown() && mezoGombok[aktualisSor][aktualisOszlop].isEnabled()) {
							zaszloElhelyezese(aktualisSor, aktualisOszlop);
						} else if (mezoGombok[aktualisSor][aktualisOszlop].getText().equals("P")) {
							zaszloTorlese(aktualisSor, aktualisOszlop);
						} else if (mezoGombok[aktualisSor][aktualisOszlop].isEnabled()) {
							while (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals("x") && elsoLepes) {
								mezo = mezoLetrehozasa(sorokSzama, oszlopokSzama, aknakSzama);
							}
							mezoFelfedese(aktualisSor, aktualisOszlop);
							idomero.requestFocus(); // gomb kijelölésének elkerülése

							aknaraLepes = false;
							uresMezo = false;
							if (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals("x")) {
								aknaraLepes = true;
							} else if (Character.toString(mezo[aktualisSor][aktualisOszlop]).equals(" ")) {
								uresMezo = true;
							}

							if (!aknaraLepes) {
								if (elsoLepes) { // időmérő elindítása
									ActionListener listener = new ActionListener() {
										public void actionPerformed(ActionEvent event) {
											idomero.setText("Idő: " + Integer.toString(++ido));
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
						// System.out.println("Maradék mező: " + maradekMezo);
					}
				});

				c.gridx = j;
				c.gridy = i;
				foAblak.add(mezoGombok[i][j], c);
			}
		} // ciklus vége

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
		foAblak.setVisible(true);
		pack();
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
				if (mezo[sor][oszlop] == 'x') { // ha akna, tovább lép a ciklus
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

		/*for (int sor = 0; sor < sorokSzama; sor++) {
			for (int oszlop = 0; oszlop < oszlopokSzama; oszlop++) {
				System.out.print(mezo[sor][oszlop]);
			}
			System.out.println();
		}*/

		return mezo;
	}

	private void segitsegKerese(int sor, int oszlop, int sorokSzama, int oszlopokSzama, String nehezsegiSzint) {
		if (maradekSegitseg > 0) {
			if (elsoLepes) { // időmérő elindítása
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						idomero.setText("Idő: " + Integer.toString(++ido));
					}
				};
				szamlalo = new Timer(1000, listener);
				szamlalo.start();
				elsoLepes = false;
			}
			ido += 30;
			segitsegSzamlalo.setText("Maradék segítség: " + --maradekSegitseg);
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (sor + i < sorokSzama && sor + i >= 0 && oszlop + j < oszlopokSzama && oszlop + j >= 0) {
						if (Character.toString(mezo[sor + i][oszlop + j]).equals("x")) {
							zaszloElhelyezese(sor + i, oszlop + j);
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
				if (mezoGombok[i][j].getText().equals("P") && Character.toString(mezo[i][j]).equals("x")) {
					// zaszloTorlese(i, j);
					mezoGombok[i][j].setForeground(Color.RED);
					mezoGombok[i][j].setEnabled(false);
				} else if (Character.toString(mezo[i][j]).equals("x")) {
					mezoGombok[i][j].setText("X");
					mezoGombok[i][j].setForeground(Color.RED);
					mezoGombok[i][j].setEnabled(false);

				}
			}
		}
	}

	private void uresMezokFelfedese(final int sorokSzama, final int oszlopokSzama, final int aktualisSor, final int aktualisOszlop) {
		int tempOszlop = aktualisOszlop;
		int tempSor = aktualisSor;
		/*
		 * lefelé indul
		 */
		while (++tempSor < sorokSzama && Character.toString(mezo[tempSor][aktualisOszlop]).equals(" ")
				&& mezoGombok[tempSor][aktualisOszlop].isEnabled()) {
			/*
			 * felfedi az aktuális sort (++sor amég üres) addig megy lefelé, amég üres a mező
			 */
			mezoFelfedese(tempSor, aktualisOszlop);
			/*
			 * közben minden üres sorban elindul jobbra
			 */
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& mezoGombok[tempSor][tempOszlop].isEnabled()) {
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
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ") && mezoGombok[tempSor][tempOszlop].isEnabled()) { // balra
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
		 * tempSor az utolsó üres sor utáni sor pozícióján áll az oszlop pedig az eredeti
		 */
		if (tempSor < sorokSzama) {
			mezoFelfedese(tempSor, aktualisOszlop);
		}
		tempOszlop = aktualisOszlop;
		tempSor = aktualisSor;
		/*
		 * felfelé indul
		 */
		while (--tempSor >= 0 && Character.toString(mezo[tempSor][aktualisOszlop]).equals(" ") && mezoGombok[tempSor][aktualisOszlop].isEnabled()) {
			mezoFelfedese(tempSor, aktualisOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& mezoGombok[tempSor][tempOszlop].isEnabled()) { // jobbra
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
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ") && mezoGombok[tempSor][tempOszlop].isEnabled()) { // balra
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
				&& mezoGombok[aktualisSor][tempOszlop].isEnabled()) {
			mezoFelfedese(aktualisSor, tempOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& mezoGombok[tempSor][tempOszlop].isEnabled()) {
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
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ") && mezoGombok[tempSor][tempOszlop].isEnabled()) {
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
		while (--tempOszlop >= 0 && Character.toString(mezo[aktualisSor][tempOszlop]).equals(" ") && mezoGombok[aktualisSor][tempOszlop].isEnabled()) {
			mezoFelfedese(aktualisSor, tempOszlop);
			while (true) {
				while (++tempOszlop < oszlopokSzama && Character.toString(mezo[tempSor][tempOszlop]).equals(" ")
						&& mezoGombok[tempSor][tempOszlop].isEnabled()) {
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
				while (--tempOszlop >= 0 && Character.toString(mezo[tempSor][tempOszlop]).equals(" ") && mezoGombok[tempSor][tempOszlop].isEnabled()) {
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
		Object[] gombok = { "Új játék", "Kilépés" };
		int valasztas = JOptionPane.showOptionDialog(rootPane, "Vesztettél! Mit kívánsz tenni?", "Aknakereső", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, gombok, gombok[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			setVisible(false);
			JFrame ujAblak = new NehezsegValasztas();
			ujAblak.setVisible(true);
		} else if (valasztas == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	private void zaszloElhelyezese(final int aktualisSor, final int aktualisOszlop) {
		mezoGombok[aktualisSor][aktualisOszlop].setText("P");
	}

	private void zaszloTorlese(final int aktualisSor, final int aktualisOszlop) {
		mezoGombok[aktualisSor][aktualisOszlop].setText(" ");
	}

	private void mezoFelfedese(final int aktualisSor, final int aktualisOszlop) {
		if (mezoGombok[aktualisSor][aktualisOszlop].isEnabled()) {
			maradekMezo--;
			mezoGombok[aktualisSor][aktualisOszlop].setText(Character.toString(mezo[aktualisSor][aktualisOszlop]));
			mezoGombok[aktualisSor][aktualisOszlop].setEnabled(false);
			if (mezoGombok[aktualisSor][aktualisOszlop].getText().equals("1")) {
				mezoGombok[aktualisSor][aktualisOszlop].setForeground(Color.BLUE);
			} else if (mezoGombok[aktualisSor][aktualisOszlop].getText().equals("2")) {
				mezoGombok[aktualisSor][aktualisOszlop].setForeground(Color.ORANGE);
			} else {
				mezoGombok[aktualisSor][aktualisOszlop].setForeground(Color.RED);
			}
		}
	}
}