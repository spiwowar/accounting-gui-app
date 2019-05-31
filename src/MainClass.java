import java.awt.*;

/**
 * Created by Szymon on 2017-04-13.
 */
public class MainClass {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}
