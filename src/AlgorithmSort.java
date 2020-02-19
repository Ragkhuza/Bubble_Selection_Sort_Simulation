import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class AlgorithmSort {
    static Button[] boxes;
    static ArrayList<Integer> arr;
    static JFrame jframe;
    final static int ANIMATE_UP = 0;
    final static int ANIMATE_DOWN = 1;
    final static int ANIMATE_LEFT = 2;
    final static int ANIMATE_RIGHT = 3;
    static int screenWidth = 1200;
    final static int SCREEN_HEIGHT = 300;
    static int animateSpeed = 5;
    static int changeColorSpeed = 300;

    protected abstract void swap(int b1, int b2);

    static protected ArrayList<Integer> askForInput() {
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
    }

    static protected void changeColor(Button btn, Color c, boolean instant) {
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

    static protected void animate(Button btn, int num, int speed, int direction) {
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

    protected void drawBoxes() {
        System.out.println("Drawing " + arr.size() + " boxes");
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
    }
}
