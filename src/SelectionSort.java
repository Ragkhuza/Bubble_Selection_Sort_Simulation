import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SelectionSort extends AlgorithmSort {
    static Thread animationThread;
    public SelectionSort(final JFrame mainFrame, ArrayList<Integer> arr) {
        AlgorithmSort.arr = arr;

        screenWidth = 100 + (arr.size() * 75);
        jframe = new JFrame("Selection Sort Simulation");
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

        // single instance buttons to keep track of our boxes
        boxes = new Button[arr.size()];

        drawBoxes();

        Button btnMove = new Button("Sort (Selection Sort)");
        btnMove.setBounds((screenWidth /2) - 50, (SCREEN_HEIGHT - 30) - 80, 120, 50);
        btnMove.addActionListener(actionEvent -> {
            animationThread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < arr.size()-1; i++) {
                        changeColor(boxes[i], Color.CYAN, false);
                        printArray(arr, i);
                        // Find the minimum element in unsorted array
                        int min_idx = i;
                        for (int j = i+1; j < arr.size(); j++) {
                            changeColor(boxes[j], Color.RED, false);
                            changeColor(boxes[j], Color.WHITE, false);
                            if (arr.get(j) <= arr.get(min_idx)) {

                                // change the color of previous minimum box to green
                                if (j > 0)
                                    if(boxes[min_idx].getBackground() == Color.GREEN)
                                        changeColor(boxes[min_idx], Color.WHITE, true);

                                min_idx = j;
                                changeColor(boxes[min_idx], Color.GREEN, false);
                            }
                        }

                        // Swap the found minimum element with the first element
                        int temp = arr.get(min_idx);
                        arr.set(min_idx, arr.get(i));
                        arr.set(i, temp);

                        if (!(i >= arr.size()))
                            swap(i, min_idx);

                        changeColor(boxes[i], Color.WHITE, false);
                    }
                }
            });
            animationThread.start();

        });
        jframe.add(btnMove);

        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);

        showLegends(jframe);
        showGroupBanner(jframe);

        jframe.setVisible(true);
    }

    protected void swap(int b1, int b2) {
        System.out.println("SWAPPING b1 : b2 || " + b1 + " : " + b2);
        System.out.println("Arr1 : boxes1 || " + arr.get(b1) + " : " + boxes[b1].getLabel());
        System.out.println("Arr2 : boxes2 || " + arr.get(b2) + " : " + boxes[b2].getLabel());

        animate(boxes[b1], 50, animateSpeed, ANIMATE_UP);
        animate(boxes[b2], 50, animateSpeed, ANIMATE_UP);

        System.out.println("Moving b1 from " + boxes[b1].getX() + " to right:  " + boxes[b2].getX());
        int tempXX = boxes[b2].getX() - boxes[b1].getX();
        animate(boxes[b1], boxes[b2].getX() - boxes[b1].getX(), animateSpeed, ANIMATE_RIGHT);
        System.out.println("Moving b2 from " + boxes[b2].getX() + " to left:  " + tempXX);
        animate(boxes[b2], tempXX, animateSpeed, ANIMATE_LEFT);

        animate(boxes[b1], 50, animateSpeed, ANIMATE_DOWN);
        animate(boxes[b2], 50, animateSpeed, ANIMATE_DOWN);

        int tempX = boxes[b1].getX();
        int tempY = boxes[b1].getY();
        boxes[b1].setLocation(boxes[b2].getX(), boxes[b2].getY());
        boxes[b2].setLocation(tempX, tempY);

        String lbl = boxes[b1].getLabel();
        boxes[b1].setLabel(boxes[b2].getLabel());
        boxes[b2].setLabel(lbl);

        changeColor(boxes[b1], Color.WHITE, true);
        changeColor(boxes[b2], Color.WHITE, true);
    }

}