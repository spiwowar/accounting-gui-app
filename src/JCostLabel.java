import javax.swing.*;

/**
 * Created by Szymon on 2017-04-20.
 */
public class JCostLabel extends JLabel {

    public JCostLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public JCostLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public JCostLabel(String text) {
        super(text);
    }

    public JCostLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public JCostLabel(Icon image) {
        super(image);
    }

    public JCostLabel() {
        super();
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
