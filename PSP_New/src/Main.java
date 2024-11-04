import java.io.FileNotFoundException;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        SE_STORE store = new SE_STORE();
        store.Read_Add_category();
        store.Read_Add_member();
        store.Read_Add_product();
        store.Start_STORE();

    }
}