import javax.swing.JFrame;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BubbleSort extends AlgorithmSort {
    /*static Button[] boxes;
    static ArrayList<Integer> arr;
    static JFrame jframe;
    final static int ANIMATE_UP = 0;
    final static int ANIMATE_DOWN = 1;
    final static int ANIMATE_LEFT = 2;
    final static int ANIMATE_RIGHT = 3;*/

/*    public static void main(String[] args) {
        BubbleSort bubbleSortSimulation = new BubbleSort();
    }*/

    public BubbleSort() {
        arr = askForInput();

        int screenWidth = 1200;
        int screenHeight = 300;
        Button[] button = new Button[arr.size()];
        jframe = new JFrame("Bubble Sort Simulation");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(null);

        // single instance btns to keep track of our boxes
        boxes = new Button[arr.size()];

        drawBoxes();

        Button btn_move = new Button("Sort (Bubble Sort)");
        btn_move.setBounds((screenWidth/2) - 50, (screenHeight - 30) - 80, 120, 50);
        btn_move.addActionListener(actionEvent -> {
                printArray(arr, 0);
                int temp = 0;
                for(int i=0; i < arr.size(); i++) {
                    for(int j=1; j < (arr.size()-i); j++) {
                        if(arr.get(j-1) > arr.get(j)) {
                            //swap elements
                            temp = arr.get(j-1);
                            arr.set(j-1, arr.get(j));
                            arr.set(j, temp);

                            System.out.println("Swapping" + arr.get(j-1) + " and " + arr.get(j));

                            swap(j-1, j);
                        }
                    }
                    printArray(arr, i + 1);
                }
        });
        jframe.add(btn_move);

        jframe.setSize(screenWidth, screenHeight);
        jframe.setLocationRelativeTo(null);
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

    /*static protected void animate(Button btn, int num, int speed, int direction) {
        switch (direction){
            case ANIMATE_UP:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX(), btn.getY() - 1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ANIMATE_DOWN:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX(), btn.getY() + 1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ANIMATE_LEFT:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX() - 1, btn.getY());
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ANIMATE_RIGHT:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX() + 1, btn.getY());
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                System.out.print("ERROR IN ANIMATE FUNCTION");
        }
    }*/

    /*protected void drawBoxes() {
        for (int i = 0; i < arr.size(); i++) {
            boxes[i] = new Button(arr.get(i) + "");
            boxes[i].setBounds(i * 70, 80, 50, 50);
            jframe.add(boxes[i]);
        }
    }*/

}