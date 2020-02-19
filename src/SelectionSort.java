import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SelectionSort extends AlgorithmSort {
    /*static Button[] boxes;
    static ArrayList<Integer> arr;
    static JFrame jframe;
    final static int ANIMATE_UP = 0;
    final static int ANIMATE_DOWN = 1;
    final static int ANIMATE_LEFT = 2;
    final static int ANIMATE_RIGHT = 3;
    static int screenWidth = 1200;
    final static int SCREEN_HEIGHT = 300;
    static int animateSpeed = 5;
    static int changeColorSpeed = 300;*/

/*    public static void main(String[] args) {
        SelectionSort bubbleSortSimulation = new SelectionSort();
    }*/

    public SelectionSort() {
        arr = askForInput();

        screenWidth = 100 + (arr.size() * 75);
        Button[] button = new Button[arr.size()];
        jframe = new JFrame("Selection Sort Simulation");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(null);

        // single instance buttons to keep track of our boxes
        boxes = new Button[arr.size()];

        drawBoxes();

        Button btnMove = new Button("Sort (Selection Sort)");
        btnMove.setBounds((screenWidth /2) - 50, (SCREEN_HEIGHT - 30) - 80, 120, 50);
        btnMove.addActionListener(actionEvent -> {
                for (int i = 0; i < arr.size()-1; i++)
                {
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
//                    arr.get(min_idx) = arr.get(i);
//                    arr.get(i) = temp;
                    arr.set(i, temp);

                    if (!(i >= arr.size()))
                        swap(i, min_idx);

                    changeColor(boxes[i], Color.WHITE, false);
                }

        });
        jframe.add(btnMove);

        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

    /*static private ArrayList<Integer> askForInput() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        String input = "";

        while (input.isEmpty()) {
            input = JOptionPane.showInputDialog("Please input numbers: (separated by space)");
            arr = new ArrayList<Integer>();
            String[] inputArr = input.split(" ");

            for (String inp : inputArr)
                arr.add(Integer.parseInt(inp));

            System.out.print("Inputs: ");
            for (Integer i : arr)
                System.out.print(i + " ");
        }
        return arr;
    }*/

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
    /*
    static private void changeColor(Button btn, Color c, boolean instant) {
        int speed = changeColorSpeed;

        if (instant)
            speed = 0;

        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btn.setBackground(c);
    }

    static private void animate(Button btn, int num, int speed, int direction) {
        System.out.println("Animating with speed of : " + speed);
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
    }

    private void drawBoxes() {
        for (int i = 0; i < arr.size(); i++) {
            boxes[i] = new Button(arr.get(i) + "");
            boxes[i].setBounds((i * 70) + (screenWidth / arr.size()), 80, 50, 50);
            jframe.add(boxes[i]);
        }
    }

    static void printArray(ArrayList<Integer> arr, int pass) {
        System.out.println("pass: " + pass);
        for (int num : arr) System.out.print(num + " ");
        System.out.println("\n");
    }*/

}