import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    int confirmExit = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Máte neuložené změny, opravdu chcete ukončit aplikaci?",
                            "Ukončit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (confirmExit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            mainFrame.setVisible(true);
        });
    }
}
