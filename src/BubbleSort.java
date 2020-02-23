import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BubbleSort extends AlgorithmSort {
    Thread animationThread;
    String BTN_LABEL = "Sort (Bubble Sort)";

    public BubbleSort(final JFrame mainFrame) {
        super();

        System.out.println("oldArr in construct" + oldInputArr);

        screenWidth = 225 + (inputArr.size() * 75);
        jframe = new JFrame("Bubble Sort Simulation");
        jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        jframe.setLayout(new BorderLayout());
        jframe.setContentPane(new JLabel(new ImageIcon(getClass().getResource(MainMenu.BACKGROUND_IMG))));
        jframe.setLayout(null);

        jframe.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                // @DOGGO maybe I can optimize these
                // close if list was entered but not animated
                if (animationThread == null) {
                    jframe.dispose();
                    mainFrame.setVisible(true);
                    return;
                }

                // @DOGGO this is my last resort
                // if animation is still on going stop all the system
                if(animationThread.isAlive()) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Closing this window while animating will terminate the whole program.", "Message", JOptionPane.OK_CANCEL_OPTION);

                    if (confirm == 0)
                        System.exit(0);
                    else
                        jframe.setVisible(true);
                } else {
                    jframe.dispose();
                    mainFrame.setVisible(true);
                }
            }
        });

        // single instance btns to keep track of our boxes
        boxes = new Button[inputArr.size()];

        createBoxes();

        jframe.add(createSortingButton());
        jframe.add(createNextPassButton());

        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);

        showGroupBanner(jframe);
        showLegends(jframe);

        jframe.setVisible(true);
    }

    public Button createSortingButton() {
        Button btnStartBubbleSort = new Button(BTN_LABEL);
        btnStartBubbleSort.setBounds((screenWidth/2) - BTN_LABEL.length() * 4, (SCREEN_HEIGHT - 30) - 80, 120, 50);
        btnStartBubbleSort.addActionListener(actionEvent -> {
            if (btnStartBubbleSort.getLabel().equals(BTN_LABEL)) {
                btnStartBubbleSort.setEnabled(false);
                animationThread = new Thread(() -> {
                    printArray(inputArr, 0);
                    int lastArr = inputArr.size();
                    int temp = 0;
                    for (int i = 0; i < maxPass; i++) {
                        for (int j = 1; j < (inputArr.size() - i); j++) {
                            changeColor(boxes[j - 1], Color.RED, false);
                            changeColor(boxes[j], Color.RED, false);
                            if (inputArr.get(j - 1) > inputArr.get(j)) {
                                changeColor(boxes[j - 1], Color.GREEN, false);
                                changeColor(boxes[j], Color.GREEN, true);
                                //swap elements
                                temp = inputArr.get(j - 1);
                                inputArr.set(j - 1, inputArr.get(j));
                                inputArr.set(j, temp);

                                System.out.println("Swapping" + inputArr.get(j - 1) + " and " + inputArr.get(j));

                                swap(j - 1, j, "bubble_sort");
                            }
                            changeColor(boxes[j - 1], Color.WHITE, true);
                            changeColor(boxes[j], Color.WHITE, true);
                        }
                        changeColor(boxes[--lastArr], Color.CYAN, false);
                        printArray(inputArr, i + 1);
                    }

                    btnStartBubbleSort.setLabel("Reset");
                    btnStartBubbleSort.setEnabled(true);
                });
                animationThread.start();
            } else if (btnStartBubbleSort.getLabel().equals("Reset")) {
                resetBoxes();
                btnStartBubbleSort.setLabel(BTN_LABEL);
            }
        });

        return btnStartBubbleSort;
    }

    public Button createNextPassButton() {
        Button nextPassButton = new Button("Next Pass");
        nextPassButton.setBounds((screenWidth/2) + 70, (SCREEN_HEIGHT - 30) - 80, 70, 50);
        nextPassButton.addActionListener(e -> {

        });
        return nextPassButton;
    }

}