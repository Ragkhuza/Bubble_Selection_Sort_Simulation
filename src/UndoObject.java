import java.util.ArrayList;

public class UndoObject {
    ArrayList<Integer> inputList;
    int currentPass, limiter;

    UndoObject(ArrayList<Integer> inputList, int currentPass, int limiter) {
        this.inputList = inputList;
        this.currentPass = currentPass;
        this.limiter = limiter;
    }
}
