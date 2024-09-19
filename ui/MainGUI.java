package ui;

import javax.swing.SwingUtilities;
import java.io.FileNotFoundException;

//EFFECTS: Starts the watchlist application
public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new WatchlistAppGUI().display();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
