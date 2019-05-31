import javax.swing.*;

/**
 * Created by Szymon on 2017-04-20.
 */
public class JPersonCheckBox extends JCheckBox {
    public JPersonCheckBox() {
        super();
    }

    public JPersonCheckBox(Icon icon) {
        super(icon);
    }

    public JPersonCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public JPersonCheckBox(String text) {
        super(text);
    }

    public JPersonCheckBox(Action a) {
        super(a);
    }

    public JPersonCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    public JPersonCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    public JPersonCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }


    private Cost cost = null;
    private Person person = null;

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Cost getCost() {
        return cost;
    }

    public Person getPerson() {
        return person;
    }
}
