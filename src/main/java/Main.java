import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Vytvoření hlavního okna aplikace
            MainFrame mainFrame = new MainFrame();

            // Nastavení, aby se aplikace neukončila automaticky po zavření hlavního okna
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Přidání posluchače pro událost zavření hlavního okna
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    // Zobrazení dialogového okna s možností potvrdit ukončení aplikace
                    int confirmExit = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Máte neuložené změny, opravdu chcete ukončit aplikaci?",
                            "Ukončit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    // Pokud uživatel potvrdí ukončení aplikace, ukončí se celý program
                    if (confirmExit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            // Nastavení hlavního okna jako viditelné, aby se zobrazilo uživateli
            mainFrame.setVisible(true);
        });
    }
}
