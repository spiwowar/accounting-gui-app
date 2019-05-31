import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Szymon on 2017-04-17.
 */
public class AddCostDialog extends JDialog {

    JLabel mainLabel = new JLabel("Provide cost title");
    JLabel costNameLabel = new JLabel("Cost name");
    JLabel costValueLabel = new JLabel("Cost value");
    JLabel costQtyLabel = new JLabel("Cost quantity");
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");
    JTextField titleTextField = new JTextField();
    JTextField valueTextField = new JTextField();
    JTextField qtyTextField = new JTextField();

    JPanel panel = new JPanel();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    public AddCostDialog() {
        super();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);
        setTitle("New person");
        setLocationRelativeTo(null);

        titleTextField.setColumns(10);
        valueTextField.setColumns(10);
        qtyTextField.setColumns(10);

        Insets insets = new Insets(10, 10, 10, 10);

        setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = insets;
        add(mainLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        add(costNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        add(titleTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        add(costValueLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        add(valueTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(costQtyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(qtyTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(okButton, gbc);


        gbc.gridx = 1;
        gbc.gridy = 4;
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

                    String costName = titleTextField.getText();
                    Double costValue;
                    Double costQty;

                    try {
                        costValue = Double.valueOf(valueTextField.getText());
                    } catch (NumberFormatException e2) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost value must me a double number (dot instead of comma)!");
                        return;
                    }

                    try {
                        costQty = Double.valueOf(qtyTextField.getText());
                    } catch (NumberFormatException e3) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost quantity must me a double number (dot instead of comma)!");
                        return;
                    }

                    if (costName.isEmpty()) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost name cannot be empty!");
                        return;
                    }

                    if (costName.contains(",")) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost name cannot contain character!");
                        return;
                    }

                    if (costName.startsWith("sep=")) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost name cannot start with \"sep=\"!");
                        return;
                    }

                    if (Double.compare(costValue, Double.valueOf(0.0)) < 0) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost value cannot be less than 0!");
                        return;
                    }

                    if (Double.compare(costQty, Double.valueOf(0.0)) < 0) {
                        JOptionPane.showMessageDialog(AddCostDialog.this, "Cost quantity cannot be less than 0 \",\"!");
                        return;
                    }

                    for (Cost cost : CostManager.getCosts()) {
                        if (cost.getName().equals(costName)) {
                            JOptionPane.showMessageDialog(AddCostDialog.this, "The cost with provided name already exists!");
                            return;
                        }
                    }
                    onOkButtonClickedListener.okButtonClicked(costName, costValue, costQty);
                }
                dispose();
            }
        });
    }

    interface OnOkButtonClickedListener {
        void okButtonClicked(String text, Double value, Double qty);
    }

    private OnOkButtonClickedListener onOkButtonClickedListener = null;

    public void setOnOkButtonClickedListener(OnOkButtonClickedListener onOkButtonClickedListener) {
        this.onOkButtonClickedListener = onOkButtonClickedListener;
    }
}
