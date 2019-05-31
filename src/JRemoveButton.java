import javax.swing.*;

/**
 * Created by Szymon on 2017-04-22.
 */
public class JRemoveButton extends JButton {

    public JRemoveButton() {
        super();
    }

    public JRemoveButton(Icon icon) {
        super(icon);
    }

    public JRemoveButton(String text) {
        super(text);
    }

    public JRemoveButton(Action a) {
        super(a);
    }

    public JRemoveButton(String text, Icon icon) {
        super(text, icon);
    }

    private Integer row;

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getRow() {
        return this.row;
    }
}
