package hu.ziyan.minesweeper.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Eredmenyek extends JFrame {
	private static final long serialVersionUID = 1973724261923093669L;
	private JPanel contentPane = new JPanel();
	private JLabel lblSzuksegesIdo;
	private JLabel lblTop10Nehezseg;
	private JLabel top1;
	private JLabel top2;
	private JLabel top3;
	private JLabel top4;
	private JLabel top5;
	private JLabel top6;
	private JLabel top7;
	private JLabel top8;
	private JLabel top9;
	private JLabel top010;
	private JButton btnUjJatek = new JButton("Új játék");
	private JButton btnKilepes = new JButton("Kilépés");
	private BufferedReader be_file;
	private FileWriter ujfajl;
	private String[][] top10 = new String[10][2];
	private String fajlnev;

	private void fajlElintezese(String nehezsegiSzint, int ido) {
		if (nehezsegiSzint.equals("Kezdő")) {
			fajlnev = "eredmenyek_konnyu.txt";
		} else if (nehezsegiSzint.equals("Közepes")) {
			fajlnev = "eredmenyek_kozepes.txt";
		} else {
			fajlnev = "nehez.txt";
		}

		try {
			be_file = new BufferedReader(new FileReader(fajlnev));
		} catch (FileNotFoundException e) {
			try {
				ujfajl = new FileWriter(fajlnev);
				be_file = new BufferedReader(new FileReader(fajlnev));
			} catch (IOException e1) {
				System.err.println("Hiba: " + e1.getMessage());
			}
		}

		for (int i = 0; i < 10; i++) {
			top10[i][0] = "9999999";
		}

		StringBuilder elem = new StringBuilder();

		try {
			int i = 0;
			int temp;
			while (true) {
				if ((temp = be_file.read()) == -1) { // karaktert lépünk, ha fájlvége jön, megszakítódik a cucc
					break;
				}
				while ((char) temp != '|') { // a mezőelválasztó karakterig berakja a tömbbe az időt
					elem.append((char) temp);
					temp = be_file.read();
				}
				top10[i][0] = elem.toString();
				elem.setLength(0); // tartalom törlése
				temp = be_file.read(); // az aktuális karakter most egy '|', ezért továbblépünk 1-et
				while ((char) temp != ';') { // a mező végéig beolvassa a tömbbe a nevet
					elem.append((char) temp);
					temp = be_file.read();
				}
				top10[i][1] = elem.toString();
				elem.setLength(0); // tartalom törlése
				i++;
			}
		} catch (IOException e) {
			System.err.println("Hiba: " + e.getMessage());
		}

		sorbaRendez();

		for (int j = 0; j < 9; j++) {
			if (Integer.parseInt(top10[j][0]) == 9999999 || Integer.parseInt(top10[j][0]) > ido) {
				String nev = JOptionPane.showInputDialog("Nyertél és bekerültél a top 10-be! Adj meg egy nevet:");
				top10[9][0] = Integer.toString(ido);
				top10[9][1] = nev;
				sorbaRendez();
				break;
			}
		}

		try {
			ujfajl = new FileWriter(fajlnev);
		} catch (IOException e) {
			System.err.println("Hiba: " + e.getMessage());
		}
		
		try {
			for (int i = 0; i < 10; i++) {
				ujfajl.write(top10[i][0] + "|" + top10[i][1] + ";");
			}
			ujfajl.close();
		} catch (IOException e) {
			System.err.println("Hiba: " + e.getMessage());
		}
	}

	private void sorbaRendez() {
		Arrays.sort(top10, new Comparator<String[]>() {
			public int compare(String[] s1, String[] s2) {
				return Integer.valueOf(s1[0]).compareTo(Integer.valueOf(s2[0]));
			}
		});
	}

	public Eredmenyek(String nehezsegiSzint, int ido) {
		setTitle("Aknakeres\u0151");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 312);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblSzuksegesIdo = new JLabel("Nyertél! Szükséges idő: " + ido + " másodperc");
		lblSzuksegesIdo.setBounds(5, 5, 434, 22);
		lblSzuksegesIdo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSzuksegesIdo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblTop10Nehezseg = new JLabel("Top 10 - " + nehezsegiSzint + ":");
		lblTop10Nehezseg.setBounds(15, 38, 419, 49);
		lblTop10Nehezseg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTop10Nehezseg.setHorizontalAlignment(SwingConstants.CENTER);

		if (!nehezsegiSzint.equals("Egyedi")) {
				fajlElintezese(nehezsegiSzint, ido);
		}

		top1 = new JLabel("Egyéni módban nincsen toplista.");
		if(!nehezsegiSzint.equals("Egyedi")) {
			top6.setText("1. " + top10[0][1] + " - " + top10[0][0] + " mp");
		}
		top1.setBounds(56, 93, 190, 16);
		top1.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top6 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[5][0]) != 9999999) {
			top6.setText("6. " + top10[5][1] + " - " + top10[5][0] + " mp");
		}
		top6.setBounds(247, 93, 170, 16);
		top6.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top2 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[1][0]) != 9999999) {
			top2.setText("2. " + top10[1][1] + " - " + top10[1][0] + " mp");
		}
		top2.setBounds(56, 120, 170, 16);
		top2.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top7 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[6][0]) != 9999999) {
			top7.setText("7. " + top10[6][1] + " - " + top10[6][0] + " mp");
		}
		top7.setBounds(247, 120, 170, 16);
		top7.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top3 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[2][0]) != 9999999) {
			top3.setText("3. " + top10[2][1] + " - " + top10[2][0] + " mp");
		}
		top3.setBounds(56, 147, 170, 16);
		top3.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top8 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[7][0]) != 9999999) {
			top8.setText("8. " + top10[7][1] + " - " + top10[7][0] + " mp");
		}
		top8.setBounds(246, 147, 170, 16);
		top8.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top4 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[3][0]) != 9999999) {
			top4.setText("4. " + top10[3][1] + " - " + top10[3][0] + " mp");
		}
		top4.setBounds(56, 174, 170, 16);
		top4.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top9 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[8][0]) != 9999999) {
			top9.setText("9. " + top10[8][1] + " - " + top10[8][0] + " mp");
		}
		top9.setBounds(246, 174, 170, 16);
		top9.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top5 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[4][0]) != 9999999) {
			top5.setText("5. " + top10[4][1] + " - " + top10[4][0] + " mp");
		}
		top5.setBounds(56, 201, 170, 16);
		top5.setFont(new Font("Tahoma", Font.PLAIN, 13));

		top010 = new JLabel("");
		if(!nehezsegiSzint.equals("Egyedi") && Integer.parseInt(top10[9][0]) != 9999999) {
			top010.setText("10. " + top10[9][1] + " - " + top10[9][0] + " mp");
		}
		top010.setBounds(246, 201, 170, 16);
		top010.setFont(new Font("Tahoma", Font.PLAIN, 13));

		btnKilepes.setBounds(345, 250, 89, 23);
		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnUjJatek.setBounds(10, 250, 89, 23);
		btnUjJatek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//JFrame ujAblak = new MinesweeperGUI();
				//ujAblak.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(lblSzuksegesIdo);
		contentPane.add(lblTop10Nehezseg);
		contentPane.add(btnUjJatek);
		contentPane.add(btnKilepes);
		contentPane.add(top4);
		contentPane.add(top9);
		contentPane.add(top3);
		contentPane.add(top8);
		contentPane.add(top2);
		contentPane.add(top7);
		contentPane.add(top1);
		contentPane.add(top6);
		contentPane.add(top5);
		contentPane.add(top010);
	}
}
