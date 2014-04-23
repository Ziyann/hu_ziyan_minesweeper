package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.view.dialogs.AboutDialog;
import hu.ziyan.minesweeper.view.dialogs.DifficultyDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class BoardMenuBar extends JMenuBar implements ActionListener {

    private static final long serialVersionUID = 406089122200172021L;
    private static final String seperator = "seperator";
    private ViewController gui;

    BoardMenuBar(final ViewController gui) {
        super();
        this.gui = gui;

        createMenuPoint(Labels.game, Labels.new_game, seperator, Labels.difficulty, seperator, Labels.exit_label);
        createMenuPoint(Labels.help, Labels.about);
    }

    private void createMenuPoint(String name, String... subnames) {
        JMenu menu = new JMenu(name);

        this.add(menu);

        for (String subname : subnames) {
            if (subname == seperator) {
                menu.addSeparator();
            } else {
                JMenuItem menuItem = new JMenuItem(subname);
                menu.add(menuItem);
                menuItem.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals(Labels.difficulty)) {
            new DifficultyDialog(gui, true);
        } else if (actionCommand.equals(Labels.new_game)) {
            gui.getController().newGame(gui.getController().getRows(), gui.getController().getColumns(),
                    gui.getController().getMines());
        } else if (actionCommand.equals(Labels.exit_label)) {
            System.exit(0);
        } else if (actionCommand.equals(Labels.about)) {
            new AboutDialog(gui, true);
        }

    }

}
