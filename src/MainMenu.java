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
    private static JComboBox<String> JComboOptions;
    private final static int screenWidth = 320;
    private final static int SCREEN_HEIGHT = 400;
    private static JFrame jframe;
    private static JButton[] jButtons;
    private final static int BUBBLE_SORT = 1;
    private final static int SELECTION_SORT = 2;
    private static FloatControl gainControl;
    private static JButton muteButton;
    public final static String BACKGROUND_IMG = "bg.jpg";
    private static ArrayList<String> previousInputs = new ArrayList<>();

    public static void main(String[] args) {
        new MainMenu();
        playBackgroundMusic();
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

    private static int showInputDialog() {
        String[] p = new String[previousInputs.size()];

        for (int i = 0, j = (p.length - 1); i < p.length; i++, j--)
            p[i] = previousInputs.get(j);

        JComboOptions = new JComboBox<String>(p);
        JComboOptions.setEditable(true);
        AutoCompleteDecorator.decorate(JComboOptions);

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Please input at least 2 numbers: (separated by ONE space)"),
                JComboOptions,
        };

        return JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.OK_CANCEL_OPTION);
    }

    private void updatePreviousInputs() {
        // update previous inputs and check for exception (just in case)
        try {
            // dont add duplicates
            if (!previousInputs.contains(JComboOptions.getSelectedItem().toString()))
                previousInputs.add(JComboOptions.getSelectedItem().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Warning: Please contact Doggo. Reason: MM@131");
        }
    }

    // get input from user & verify if correct
    protected static ArrayList<Integer> getInput() throws NullPointerException{
        ArrayList<Integer> arr = new ArrayList<Integer>();
        String input = "";

        // when user clicked cancel
        if (showInputDialog() != 0)
            throw new NullPointerException("Err in getInput #1");

        // will not really trigger the exception (hopefully)
        // but doggo wants to make sure everything is working fine
        try {
            input = JComboOptions.getSelectedItem().toString();
        } catch (Exception e) {
            throw new NullPointerException("Err in getInput #2");
        }

//        input = JOptionPane.showInputDialog("Please input at least 2 numbers: (separated by ONE space)");
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
                AlgorithmSort.inputArr = getInput();
                pass = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Input cancelled");
                System.out.println("ERROR Blank Input " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("ERROR in askForInput: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Please input a valid list of number!");
            }
        }

        if (pass) {
            // if user clicked cancel don't proceed to sorting
            if (AlgorithmSort.inputArr.size() <= 0) {
                JOptionPane.showMessageDialog(null, "Please input at least two integers!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // add to previous inputs
            updatePreviousInputs();
            // else start the sorting
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
