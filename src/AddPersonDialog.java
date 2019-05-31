import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Szymon on 2017-04-17.
 */
public class AddPersonDialog extends JDialog {

    JLabel label = new JLabel("Provide user name");
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");
    JTextField textField = new JTextField();

    JPanel panel = new JPanel();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    public AddPersonDialog() {
        super();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);
        setTitle("New user");

        Insets insets = new Insets(30, 30, 30, 30);

        setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.insets = insets;

        add(label, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        add(textField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(okButton, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(cancelButton, gbc);

        pack();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onOkButtonClickedListener != null) {
                    String userName = textField.getText();
                    if (userName.isEmpty()) {
                        JOptionPane.showMessageDialog(AddPersonDialog.this, "Person name cannot be empty!");
                        return;
                    }

                    if (userName.contains(",")) {
                        JOptionPane.showMessageDialog(AddPersonDialog.this, "Person name cannot contain character \",\"!");
                        return;
                    }
                    for (Person person : PeopleManager.getPeople()) {
                        if (person.getName().equals(userName)) {
                            JOptionPane.showMessageDialog(AddPersonDialog.this, "The person with provided name already exists!");
                            return;
                        }
                    }

                    onOkButtonClickedListener.okButtonClicked(userName);
                }
                dispose();
            }
        });
    }

    interface OnOkButtonClickedListener {
        void okButtonClicked(String text);
    }

    private OnOkButtonClickedListener onOkButtonClickedListener = null;

    public void setOnOkButtonClickedListener(OnOkButtonClickedListener onOkButtonClickedListener) {
        this.onOkButtonClickedListener = onOkButtonClickedListener;
    }
}
