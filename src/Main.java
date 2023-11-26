import Ermakov.PartOne.ListManagerApp;
import Ermakov.PartTwo.LibraryApp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListManagerApp app = new ListManagerApp();
            app.setVisible(true);
        });

        SwingUtilities.invokeLater(() -> {
            LibraryApp app = new LibraryApp();
            app.setVisible(true);
        });
    }
}