import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BubbleSort extends AlgorithmSort {
    Thread animationThread;
    String BTN_LABEL = "Sort (Bubble Sort)";

    public BubbleSort(final JFrame mainFrame, ArrayList<Integer> arr) {
        AlgorithmSort.arr = arr;
        AlgorithmSort.oldArr = arr;

        screenWidth = 100 + (arr.size() * 75);
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
        boxes = new Button[arr.size()];

        createBoxes();

        Button btn_move = new Button(BTN_LABEL);
        btn_move.setBounds((screenWidth/2) - 50, (SCREEN_HEIGHT - 30) - 80, 120, 50);
        btn_move.addActionListener(actionEvent -> {
            if (btn_move.getLabel().equals(BTN_LABEL)) {
                animationThread = new Thread(new Runnable() {
                    public void run() {
                        printArray(arr, 0);
                        int lastArr = arr.size();
                        int temp = 0;
                        for (int i = 0; i < arr.size(); i++) {
                            for (int j = 1; j < (arr.size() - i); j++) {
                                changeColor(boxes[j - 1], Color.RED, false);
                                changeColor(boxes[j], Color.RED, false);
                                if (arr.get(j - 1) > arr.get(j)) {
                                    changeColor(boxes[j - 1], Color.GREEN, false);
                                    changeColor(boxes[j], Color.GREEN, true);
                                    //swap elements
                                    temp = arr.get(j - 1);
                                    arr.set(j - 1, arr.get(j));
                                    arr.set(j, temp);

                                    System.out.println("Swapping" + arr.get(j - 1) + " and " + arr.get(j));

                                    swap(j - 1, j);
                                }
                                changeColor(boxes[j - 1], Color.WHITE, true);
                                changeColor(boxes[j], Color.WHITE, true);
                            }
                            changeColor(boxes[--lastArr], Color.CYAN, false);
                            printArray(arr, i + 1);
                        }

                        btn_move.setLabel("Reset");
                    }
                });
                animationThread.start();
            } else if (btn_move.getLabel().equals("Reset")) {
                resetBoxes();
                btn_move.setLabel(BTN_LABEL);
            }
        });
        jframe.add(btn_move);

        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);

        showGroupBanner(jframe);
        showLegends(jframe);

        jframe.setVisible(true);
    }

    protected void swap(int b1, int b2) {
        animate(boxes[b1], 50, 1, ANIMATE_UP);
        animate(boxes[b2], 50, 1, ANIMATE_UP);

        animate(boxes[b1], 70, 1, ANIMATE_RIGHT);
        animate(boxes[b2], 70, 1, ANIMATE_LEFT);

        animate(boxes[b1], 50, 1, ANIMATE_DOWN);
        animate(boxes[b2], 50, 1, ANIMATE_DOWN);

        int tempX = boxes[b1].getX();
        boxes[b1].setLocation(boxes[b2].getX(), boxes[b2].getY());
        boxes[b2].setLocation(tempX, boxes[b2].getY());

        String lbl = boxes[b1].getLabel();
        boxes[b1].setLabel(boxes[b2].getLabel());
        boxes[b2].setLabel(lbl);
    }

    public void resetBoxes() {
        arr = oldArr;

        printArray(arr, -1);
        printArray(oldArr, -2);
        removeBoxes();
        createBoxes();
    }

}