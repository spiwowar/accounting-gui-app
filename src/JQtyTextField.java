import javax.swing.*;
import javax.swing.text.Document;

/**
 * Created by Szymon on 2017-04-20.
 */
public class JQtyTextField extends JTextField {

    public JQtyTextField() {
        super();
    }

    public JQtyTextField(String text) {
        super(text);
    }

    public JQtyTextField(int columns) {
        super(columns);
    }

    public JQtyTextField(String text, int columns) {
        super(text, columns);
    }

    public JQtyTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    Cost cost = null;
    Person person = null;

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


    JLabel costLabel;

    public void setCostLabel(JLabel costLabel) {
        this.costLabel = costLabel;
    }

    public JLabel getCostLabel() {
        return this.costLabel;
    }
}
