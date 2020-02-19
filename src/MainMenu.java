import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class MainMenu {
    private final static int screenWidth = 300;
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
    }

    public MainMenu() {
        final int BTN_WIDTH = 180;
        final int BTN_HEIGHT = 30;

        jframe = new JFrame("Algorithm Simulations");
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

    // get input from user
    static protected ArrayList<Integer> askForInput() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        String input = "";

                input = JOptionPane.showInputDialog("Please input numbers: (separated by ONE space)");
                arr = new ArrayList<Integer>();

                String[] inputArr = input.split(" ");

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
        ArrayList<Integer> arrayList = new ArrayList<>();

        while (!pass) {
            try {
                arrayList = askForInput();
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
            switch (sortType) {
                case BUBBLE_SORT:
                    System.out.println("Array size = " + arrayList.size());
                    new BubbleSort(jframe, arrayList);
                    jframe.setVisible(false);
                    break;
                case SELECTION_SORT:
                    new SelectionSort(jframe, arrayList);
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
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            MainMenu.class.getResourceAsStream("upbeat.wav")
                    );
                    clip.open(inputStream);
                    gainControl =
                            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("Error playing music Doggo");
                }
            }
        }).start();
    }

}
