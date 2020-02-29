import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectionSort extends AlgorithmSort {
    final static String BTN_LABEL = "Selection Sort";
    final static int BTN_RELATIVE_VAL = 30;
    static Thread animationThread;

    Button btnStartSelectionSort, nextPassButton, resetButton;

    public SelectionSort(final JFrame mainFrame) {
        super();

        screenWidth = 225 + (inputArr.size() * 75);
        jframe = new JFrame("Selection Sort Simulation");
        jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        jframe.setLayout(new BorderLayout());
        jframe.setContentPane(new JLabel(new ImageIcon(getClass().getResource(MainMenu.BACKGROUND_IMG))));
        jframe.setLayout(null);
        jframe.setResizable(false);
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
                    int confirm = JOptionPane.showConfirmDialog(null, "Closing this window while animating will terminate the whole program.",
                            "Message", JOptionPane.OK_CANCEL_OPTION);

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
        boxes = new Button[inputArr.size()];

        createBoxes();

        // ADD BUTTONS
        jframe.add(createResetButton());
        jframe.add(createSortingButton());
        jframe.add(createNextPassButton());

        jframe.setSize(screenWidth, SCREEN_HEIGHT);
        jframe.setLocationRelativeTo(null);

        showLegends(jframe, true);
        showGroupBanner(jframe);

        jframe.setVisible(true);
        updateLegends(0, 0, 0); // reset all legends to zero
        updateLegend(2, "done: 0", true);
    }

    private int doSelectionSort() {
        if (getMaxPass() <= inputArr.size()) {
            updateLegend(2, "done: ", true); // update the 3rd legend for selection sort
            System.out.println("currentPass: " + getCurrentPass());
            System.out.println("maxPass: " + getMaxPass());
            for (int i = getCurrentPass(); i <= getMaxPass() - 1; i++) {
                changeColor(boxes[i], Color.CYAN, false);
                printArray(inputArr, i);
                // Find the minimum element in unsorted array
                int min_idx = i;
                for (int j = i + 1; j < inputArr.size(); j++) {
                    updateLegend(1, j+"",false); // update current
                    updateLegend(2, "done: "+i, true); // update done

                    System.out.println("currentPass: " + getCurrentPass());
                    System.out.println("maxPass: " + getMaxPass());
                    changeColor(boxes[j], Color.RED, false);
                    changeColor(boxes[j], Color.WHITE, false);
                    if (inputArr.get(j) <= inputArr.get(min_idx)) {

                        // change the color of previous minimum box to green
                        if (j > 0)
                            if (boxes[min_idx].getBackground() == Color.GREEN)
                                changeColor(boxes[min_idx], Color.WHITE, true);

                        min_idx = j;
                        changeColor(boxes[min_idx], Color.GREEN, false);
                    }
                }

                // Swap the found minimum element with the first element
                int temp = inputArr.get(min_idx);
                inputArr.set(min_idx, inputArr.get(i));
                inputArr.set(i, temp);

                if (!(i >= inputArr.size()))
                    swap(i, min_idx,"selection_sort");

                if (i < inputArr.size() - 1)
                    updateLegend(0, (i + 1) + "", false); // update pass
                updateLegend(2, "done: "+i, true); // update done
                changeColor(boxes[i], Color.CYAN, false);
            }

        }
        enableButtons();

        return getCurrentPass();
    }

    public Button createResetButton() {
        resetButton = new Button("Reset");
        resetButton.setBounds( ((screenWidth/2) - 70) - BTN_RELATIVE_VAL, (SCREEN_HEIGHT - 30) - 80, 50, 50);
        resetButton.addActionListener(e -> {
            resetPassValues();
            resetBoxes();
        });

        return resetButton;
    }

    public Button createSortingButton() {
        btnStartSelectionSort = new Button(BTN_LABEL);
        btnStartSelectionSort.setBounds( (((screenWidth/2) + 30) - BTN_LABEL.length() * 3) - BTN_RELATIVE_VAL, (SCREEN_HEIGHT - 30) - 80, 100, 50);
        btnStartSelectionSort.addActionListener(actionEvent -> {
            setMaxPass(inputArr.size());
            disableButtons();

            animationThread = new Thread(() -> {
                doSelectionSort();
            });
            animationThread.start();

        });

        return btnStartSelectionSort;
    }

    public Button createNextPassButton() {
        nextPassButton = new Button("Next Pass");
        nextPassButton.setBounds( ((screenWidth/2) + 95) - BTN_RELATIVE_VAL, (SCREEN_HEIGHT - 30) - 80, 70, 50);
        nextPassButton.addActionListener(e -> {
            new Thread(() -> {
                nextPass();
            }).start();
        });

        return nextPassButton;
    }

    private void nextPass() {
        disableButtons();
        System.out.println("clicking next pass");
        setMaxPass(getCurrentPass() + 1);
        // execute after thread have finished
        setCurrentPass(doSelectionSort() + 1);
        enableButtons();
    }

    public void disableButtons() {
        resetButton.setEnabled(false);
        btnStartSelectionSort.setEnabled(false);
        nextPassButton.setEnabled(false);
    }

    public void enableButtons() {
        resetButton.setEnabled(true);
        btnStartSelectionSort.setEnabled(true);
        nextPassButton.setEnabled(true);
    }

}