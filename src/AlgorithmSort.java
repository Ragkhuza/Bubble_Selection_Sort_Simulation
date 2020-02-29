import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public abstract class AlgorithmSort {
    final static int ANIMATE_UP = 0, ANIMATE_DOWN = 1, ANIMATE_LEFT = 2, ANIMATE_RIGHT = 3;
    static int screenWidth = 1200, animateSpeed = 5, changeColorSpeed = 300, lastArr;
    static int currentPass, maxPass, SCREEN_HEIGHT = 300; // should made this private but can't risk errors
    static Button[] boxes;
    static ArrayList<Integer> inputArr;
    static JFrame jframe;

    JLabel[] legendsJLabels;
    ArrayList<Integer> oldInputArr;
    Stack<UndoObject> undoStack = new Stack<UndoObject>();

    public abstract Button createResetButton();
    public abstract Button createSortingButton();
    public abstract Button createNextPassButton();
    public abstract void disableButtons();
    public abstract void enableButtons();

    public AlgorithmSort() {
        oldInputArr = (ArrayList<Integer>) AlgorithmSort.inputArr.clone();
        resetPassValues();
    }

    public void updateUndoStack(ArrayList<Integer> undoInputArr, int undoCurrentPass, int lastArr) {
        System.out.println("Pushing to undoStack: " + undoInputArr);
        UndoObject undoObject = new UndoObject((ArrayList<Integer>) undoInputArr.clone(), undoCurrentPass, lastArr);
        undoStack.push(undoObject);
    }

    public void undoPass(String type) {
        System.out.println("Undoing");
        if (type.toLowerCase().equals("bubble")) {
            inputArr = undoStack.lastElement().inputList;
            setCurrentPass(undoStack.lastElement().currentPass);
            resetBoxes(inputArr);
            undoBoxesColor(undoStack.lastElement().limiter, 1);
            lastArr = undoStack.lastElement().limiter;
            System.out.println("Popped from undoStack" + undoStack.pop().inputList);
        } else if (type.toLowerCase().equals("selection")) {
            inputArr = undoStack.lastElement().inputList;
            setCurrentPass(undoStack.lastElement().currentPass);
            resetBoxes(inputArr);
            undoBoxesColor(undoStack.lastElement().limiter, 2);
            setMaxPass(undoStack.lastElement().limiter);
            System.out.println("Popped from undoStack" + undoStack.pop().inputList);
        } else {
            // in case I mistype things
            JOptionPane.showMessageDialog(null, "Doggo must see AlgorithmSort.java@51");
            System.out.println("The type was invalid, input: " + type);
        }


    }

    private void undoBoxesColor(int limiter, int type) {
        if (type == 1)
            for (int i = limiter; i < inputArr.size(); i++) {
                changeColor(boxes[i], Color.CYAN, true);
            }
        else if (type == 2)
            for (int i = 0; i < (limiter - 1); i++) {
                changeColor(boxes[i], Color.CYAN, true);
            }
        else
            System.out.println("Opps you mistype type in undoBoxes, inputted: " + type);
    }

    public void resetUndoStack() {
        undoStack = new Stack<UndoObject>();
    }

    public void resetPassValues() {
        System.out.println("Resetting pass values");
        initializePass(0, oldInputArr.size());
        lastArr = inputArr.size();
    }

    public int getCurrentPass() {
        return currentPass;
    }

    public int getMaxPass() {
        return maxPass;
    }

    public void setCurrentPass(int p) {
        currentPass = p;
    }

    public void setMaxPass(int p) {
        maxPass = p;
    }

    public void initializePass(int cp, int mp) {
        currentPass = cp;
        maxPass = mp;
    }

    protected static void changeColor(Button btn, Color c, boolean instant) {
        int speed = changeColorSpeed;

        if (instant)
            speed = 0;

        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (InterruptedException e) {
            System.out.println("[changeColor] {ignore!} InterruptedException in AlgorithmSort.java");
        }

        btn.setBackground(c);
    }

    protected static void animate(Button btn, int num, int speed, int direction) {
        System.out.println("Animating with speed of : " + speed);
        switch (direction){
            case ANIMATE_UP:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX(), btn.getY() - 1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        System.out.println("[WARNING]User interrupted the animations, Threads will keep going until destroyed.");
                    }
                }
                break;

            case ANIMATE_DOWN:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX(), btn.getY() + 1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        System.out.println("[WARNING]User interrupted the animations, Threads will keep going until destroyed.");
                    }
                }
                break;

            case ANIMATE_LEFT:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX() - 1, btn.getY());
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        System.out.println("[WARNING]User interrupted the animations, Threads will keep going until destroyed.");
                    }
                }
                break;

            case ANIMATE_RIGHT:
                for (int i = 0; i < num; i++) {
                    btn.setLocation(btn.getX() + 1, btn.getY());
                    try {
                        TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        System.out.println("[WARNING]User interrupted the animations, Threads will keep going until destroyed.");
                    }
                }
                break;

            default:
                System.out.print("ERROR IN ANIMATE FUNCTION");
        }
    }

    protected void createBoxes() {
        System.out.println("Drawing " + inputArr.size() + " boxes");
        for (int i = 0; i < inputArr.size(); i++) {
            boxes[i] = new Button(inputArr.get(i) + "");
            boxes[i].setBounds((i * 70) + (screenWidth / inputArr.size()), 80, 50, 50);
            jframe.add(boxes[i]);
        }
    }

    protected void removeBoxes() {
        System.out.println("Removing " + inputArr.size() + " boxes");
        for (int i = 0; i < inputArr.size(); i++) {
            jframe.remove(boxes[i]);
            boxes[i] = null;
        }
    }

    public void resetBoxes(ArrayList<Integer> inputList) {
        System.out.println("arr" + inputArr);
        System.out.println("old arr" + oldInputArr);

        inputArr = (ArrayList<Integer>) inputList.clone();
        System.out.println("new arr" + inputArr);

        removeBoxes();
        createBoxes();
    }

    public void resetBoxes() {
        System.out.println("arr" + inputArr);
        System.out.println("old arr" + oldInputArr);

        inputArr = (ArrayList<Integer>) oldInputArr.clone();
        System.out.println("new arr" + inputArr);

        removeBoxes();
        createBoxes();
    }

    static void printArray(ArrayList<Integer> arr, int pass) {
        System.out.println("pass: " + pass);
        for (int num : arr) System.out.print(num + " ");
        System.out.println("\n");
    }

    static void showGroupBanner(final JFrame algoFrame) {
        String[] members = {"Carlo Alejo", "Nielsen Bernardo", "Maika Calinog", "Rasheed Sto. Tomas", "Chris James Delos Reyes"};
        JLabel[] membersJLabel = new JLabel[members.length];

        int memberTotalWidth = 0;
        for (int i = 0; i < members.length; i++) {
            int memberNameWidth = (i + 1) * (members[i].length() * 10);
            membersJLabel[i] = new JLabel(members[i]);
            membersJLabel[i].setBounds(screenWidth + (150 * i) , 140, memberNameWidth,35);
            membersJLabel[i].setForeground(Color.WHITE);
            algoFrame.add(membersJLabel[i]);

            memberTotalWidth += (screenWidth) + memberNameWidth;
        }

        for (int i = 0; i < members.length; i++) {
            final int finalI = i;
            final int finalMemberTotalWidth = memberTotalWidth;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int x = membersJLabel[finalI].getX();
                    int y = membersJLabel[finalI].getY();
                    int width = membersJLabel[finalI].getWidth();
                    int height = membersJLabel[finalI].getHeight();
                    boolean reversed = true;

                    System.out.println("FINAL MEMBER TOTAL WIDTH: " + finalMemberTotalWidth);
                    while(true) {
                        reversed = !reversed;
                        for (int j = 0; j < (int)(finalMemberTotalWidth / 3); j++) {
                            try {
                                if (reversed) {
                                    TimeUnit.MILLISECONDS.sleep(10);
                                    membersJLabel[finalI].setBounds(x++, y, width, height);
                                } else {
                                    TimeUnit.MILLISECONDS.sleep(10);
                                    membersJLabel[finalI].setBounds(x--, y, width, height);
                                }
                            } catch (InterruptedException e) {
                                System.out.println("[WARNING]User interrupted the animations, Threads will keep going until destroyed.");
                            }
                        }
                    }
                }
            }).start();
        }
    }

    void updateLegend(int i, String str, boolean wholeWord) {
        if (!wholeWord)
            switch (i) {
                case 0:
                    legendsJLabels[0].setText("pass: " + str);
                    break;
                case 1:
                    legendsJLabels[1].setText("current i: " + str);
                    break;
                case 2:
                    legendsJLabels[2].setText("limit: " + str);
                    break;
                default:
                    System.out.println("ERROR in update Legend: value = " + i);
            }
        else {
            switch (i) {
                case 0:
                    legendsJLabels[0].setText(str);
                    break;
                case 1:
                    legendsJLabels[1].setText(str);
                    break;
                case 2:
                    legendsJLabels[2].setText(str);
                    break;
                default:
                    System.out.println("ERROR in update Legend: value = " + i);
            }
        }

    }

    void updateLegends(int l1, int l2, int l3) {
        legendsJLabels[0].setText("pass: " + l1+"");
        legendsJLabels[1].setText("current i: " + l2+"");
        legendsJLabels[2].setText("limit: " + l3+"");
    }

    void showLegends(final JFrame algoFrame, boolean isSelectionSort) {
        String[] legends = {"pass: ", "current i: ", "limit: ", "LOWEST"};
        legendsJLabels = new JLabel[legends.length];

        for (int i = 0; i < legends.length; i++) {
            if (!isSelectionSort && i == (legends.length - 1))
                legends[3] = "SWAPPING";
            legendsJLabels[i] = new JLabel(legends[i]);
            legendsJLabels[i].setBounds(20, 160 + (i * 20), legends[i].length() * 10,20);

            switch (i) {
                case 0:
                    legendsJLabels[0].setForeground(Color.getHSBColor(0.8f, 0.5f, 0.9f));
                    break;
                case 1:
                    legendsJLabels[1].setForeground(Color.RED);
                    break;
                case 2:
                    legendsJLabels[2].setForeground(Color.CYAN);
                    break;
                case 3:
                    legendsJLabels[3].setForeground(Color.GREEN);
                    break;
                default:
                    System.out.println("ERROR in show legends");
            }
            algoFrame.add(legendsJLabels[i]);
        }

    }

    protected void swap(int b1, int b2, String sortType) {
        if (sortType.equals("bubble_sort")) {
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

        } else if (sortType.equals("selection_sort")) {
            System.out.println("SWAPPING b1 : b2 || " + b1 + " : " + b2);
            System.out.println("Arr1 : boxes1 || " + inputArr.get(b1) + " : " + boxes[b1].getLabel());
            System.out.println("Arr2 : boxes2 || " + inputArr.get(b2) + " : " + boxes[b2].getLabel());

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
        } else {
            JOptionPane.showMessageDialog(null, "I think you mistyped sortType in swap", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}