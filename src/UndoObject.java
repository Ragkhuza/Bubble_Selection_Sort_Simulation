import java.util.ArrayList;

public class UndoObject {
    ArrayList<Integer> inputList;
    int currentPass, lastArr;

    UndoObject(ArrayList<Integer> inputList, int currentPass, int lastArr) {
        this.inputList = inputList;
        this.currentPass = currentPass;
        this.lastArr = lastArr;
    }
}
