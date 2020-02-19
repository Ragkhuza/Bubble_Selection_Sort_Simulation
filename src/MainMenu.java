import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private final static int screenWidth = 400;
    private final static int SCREEN_HEIGHT = 300;
    private static JFrame jframe;
    private static JButton[] jButtons;

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
    }

    public MainMenu() {
        final int BTN_WIDTH = 180;
        final int BTN_HEIGHT = 30;

        jframe = new JFrame("Algorithm Simulations");
        jframe.setLayout(null);
        jButtons = new JButton[3];

        for (int i = 0; i < jButtons.length; i++) {
            switch (i) {
                case 0:
                    jButtons[i] = new JButton("Bubble Sort");
                    jButtons[i].addActionListener(actionEvent -> {
                        new BubbleSort();
                        jframe.setVisible(false);
                    });
                    break;
                case 1:
                    jButtons[i] = new JButton("Selection Sort");
                    jButtons[i].addActionListener(actionEvent -> {
                        new SelectionSort();
                        jframe.setVisible(false);
                    });
                    break;
                case 2:
                    jButtons[i] = new JButton("Exit");
                    jButtons[i].addActionListener(actionEvent -> {
                        System.exit(0);
                    });
                    break;
                default:
                    System.out.println("Error in setting buttons");
            }

            jButtons[i].setSize(BTN_WIDTH, BTN_HEIGHT);
            jButtons[i].setLocation((screenWidth / 2) - (BTN_WIDTH / 2) , BTN_HEIGHT + (i * 50));

            jframe.add(jButtons[i]);
        }

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

}
