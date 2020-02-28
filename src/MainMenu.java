import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainMenu {
    private final static int screenWidth = 320;
    private final static int SCREEN_HEIGHT = 400;
    private static JFrame jframe;
    private static JButton[] jButtons;
    private final static int BUBBLE_SORT = 1;
    private final static int SELECTION_SORT = 2;
    private static FloatControl gainControl;
    private static JButton muteButton;
    public final static String BACKGROUND_IMG = "bg.jpg";

    public static void main(String[] args) {
        new MainMenu();
        playBackgroundMusic();
//        testDialog();
    }

    public MainMenu() {
        final int BTN_WIDTH = 180;
        final int BTN_HEIGHT = 30;

        jframe = new JFrame("Algorithm Simulations");
        jframe.setResizable(false);
        jframe.setLayout(new BorderLayout());
        jframe.setContentPane(new JLabel(new ImageIcon(getClass().getResource(BACKGROUND_IMG))));

        jframe.setLayout(null);
        jButtons = new JButton[4];

        for (int i = 0; i < jButtons.length; i++) {
            switch (i) {
                case 0:
                    jButtons[i] = new JButton("Bubble Sort");
                    jButtons[i].addActionListener(actionEvent -> {
                        startSorting(BUBBLE_SORT);
                    });
                    break;
                case 1:
                    jButtons[i] = new JButton("Selection Sort");
                    jButtons[i].addActionListener(actionEvent -> {
                        startSorting(SELECTION_SORT);
                    });
                    break;
                case 2:
                    jButtons[i] = new JButton("Exit");
                    jButtons[i].addActionListener(actionEvent -> {
                        System.exit(0);
                    });
                    break;
                case 3:
                    jButtons[i] = new JButton("Mute");
                    muteButton = jButtons[i];
                    jButtons[i].addActionListener(actionEvent -> {
                        if (gainControl.getValue() >= 0) {
                            muteButton.setText("Unmute");
                            gainControl.setValue(-80.0f);
                        } else {
                            muteButton.setText("Mute");
                            gainControl.setValue(1.0f);
                        }
                    });
                    break;
                default:
                    System.out.println("Error in setting buttons");
            }

            jButtons[i].setSize(BTN_WIDTH, BTN_HEIGHT);
            jButtons[i].setLocation((screenWidth / 2) - (BTN_WIDTH / 2) , BTN_HEIGHT + (i * 75));

            jframe.add(jButtons[i]);
        }

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

/*    public static void testDialog() {
        JFrame frame = new JFrame("");
        AutoCompleteDecorator decorator;
        JComboBox<String> combobox;

        combobox = new JComboBox<String>(new String[]{"","27 35 23 65 67", "78 23 45 6 75",
                "23 45 56 76 89", "23 45 67 789 9", "34 67 23 78 23 44"});
        combobox.setEditable(true);
        AutoCompleteDecorator.decorate(combobox);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(combobox);
        frame.setVisible(true);
    }*/

    // get input from user & verify if correct
    static protected ArrayList<Integer> askForInput() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        String input = "";

        input = JOptionPane.showInputDialog("Please input at least 2 numbers: (separated by ONE space)");
        arr = new ArrayList<Integer>();

        String[] inputArr = input.trim().split(" ");

        for (String inp : inputArr)
            arr.add(Integer.parseInt(inp));

        System.out.print("Inputs: ");
        for (Integer i : arr)
            System.out.print(i + " ");


        return arr;
    }

    // The main driver for each sorting algorithm
    private void startSorting(int sortType) {
        boolean pass = false;

        while (!pass) {
            try {
                AlgorithmSort.inputArr = askForInput();
                pass = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cancelled by User!");
                System.out.println("ERROR Blank Input");
                break;
            } catch (Exception e) {
                System.out.println("ERROR in askForInput");
                JOptionPane.showMessageDialog(null, "Please input a valid list of number!");
            }
        }

        // if user clicked cancel don't proceed to sorting
        if (pass) {
            if (AlgorithmSort.inputArr.size() <= 0) {
                JOptionPane.showMessageDialog(null, "Please input at least two integers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (sortType) {
                case BUBBLE_SORT:
                    System.out.println("Array size = " + AlgorithmSort.inputArr.size());
                    new BubbleSort(jframe);
                    jframe.setVisible(false);
                    break;
                case SELECTION_SORT:
                    new SelectionSort(jframe);
                    jframe.setVisible(false);
                    break;
                default:
                    System.out.println("ERROR IN switch in startSorting() MainMenu.java");
            }
        }

    }

    static private void playBackgroundMusic() {
        // This code is Doggoed
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    // mark/reset lets a stream "unread" data back to the mark point
                    // will not play in jar without this
                    InputStream audioSrc = getClass().getResourceAsStream("upbeat.wav");
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    // Put audio back to AudioInputStream Doggo
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                    clip.open(inputStream);
                    gainControl =
                            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("Error playing music Doggo: " + e.getMessage());
                }
            }
        }).start();
    }

}
