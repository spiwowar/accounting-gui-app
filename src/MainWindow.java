import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Szymon on 2017-04-13.
 */
public class MainWindow extends JFrame {

    private boolean initScreen = true;
    JScrollPane scrollPane;

    JLabel costNameLabel = new JLabel("CostName");
    JLabel costValueLabel = new JLabel("CostValue");
    JLabel costQtyLabel = new JLabel("CostQty");

    JLabel totalLabel = new JLabel("Total");
    JLabel totalCostLabel = new JLabel("TotalCost");
    ArrayList<JCostLabel> totalCostPeopleLabels = new ArrayList<>();
    JCostLabel totalCostPerson;

    int currentRow = 0;
    int currentColumn = 0;

    Insets insets = new Insets(10, 10, 10, 10);

    JButton addPersonButton = new JButton("Add person");
    JButton addCostButton = new JButton("Add cost");
    JButton saveCSVButton = new JButton("Save as CSV file");
    JButton readCSVButton = new JButton("Read CSV file");
    ArrayList<JRemoveButton> removeButtons = new ArrayList<>();
    JRemoveButton removeButton;

    JPanel mainPanel = new JPanel();
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    public MainWindow() throws HeadlessException {
        super("Accounting");
        init();
    }

    private void clearAllComp() {
        totalCostPeopleLabels.clear();
        removeButtons.clear();
        removeButtons();
        removeTotalLabels();
        mainPanel.removeAll();
        currentColumn = 0;
        currentRow = 0;
        CostManager.clear();
        PeopleManager.clear();
    }

    private void init() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 150);
        setMaximumSize(new Dimension(1505, 1700));
        setResizable(true);
        setVisible(true);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setMinimumSize(new Dimension(100, 1500));
        //scrollPane.setPreferredSize(new Dimension(1300, 1700));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.setLayout(gridBagLayout);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        add(scrollPane);

        initButtons();
        setButtons();
    }

    private void initButtons() {
        initAddPersonButton();
        initAddCostButton();
        initSaveCSVButton();
        initReadCSVButton();
    }

    private void setButtons() {
        setAddPersonButton();
        setReadCSVButton();
        setAddCostButton();
        setSaveCSVButton();
    }

    private void removeTotalLabels() {
        mainPanel.remove(totalLabel);
        mainPanel.remove(totalCostLabel);
        for (JLabel tcpLabel : totalCostPeopleLabels) {
            mainPanel.remove(tcpLabel);
        }
        totalCostPeopleLabels.clear();
    }

    private void removeButtons() {
        mainPanel.remove(addCostButton);
        mainPanel.remove(addPersonButton);
        mainPanel.remove(saveCSVButton);
    }

    private void removeRemoveCostsButtons() {
        for (JRemoveButton removeButton : removeButtons) {
            mainPanel.remove(removeButton);
        }
        removeButtons.clear();
    }

    private void addCostNameLabel(String name) {
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(new JLabel(name), gbc);
    }

    private void addCostValueLabel(String value) {
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(new JLabel(value + " zl"), gbc);
    }

    private void addCostQtyLabel(String qty) {
        gbc.gridx = 2;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(new JLabel(qty), gbc);
    }

    private void setTotalLabels() {

        if (currentRow != 0) {
            removeTotalLabels();
        }

        // ADD Total cost label
        gbc.gridx = 0;
        gbc.gridy = currentRow + 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(totalLabel, gbc);

        // Calculate total cost
        Double totalCost = 0.0;
        for (Cost costPart : CostManager.getCosts()) {
            totalCost += costPart.getValue();
        }
        totalCostLabel.setText(totalCost.toString() + " zl");

        // Add total cost value label
        gbc.gridx = 1;
        mainPanel.add(totalCostLabel, gbc);

        // Add total cost people labels
        int personCounter = 0;
        for (Person person : PeopleManager.getPeople()) {
            gbc.gridx = 2 + ++personCounter;

            Double totalPersonCost = 0.0;
            for (Cost costsPerson : person.getAllCosts()) {
                totalPersonCost += costsPerson.getValue();
            }
            totalCostPerson = new JCostLabel(totalPersonCost + " zl");
            totalCostPerson.setPerson(person);
            totalCostPeopleLabels.add(totalCostPerson);
            mainPanel.add(totalCostPerson, gbc);
        }
    }

    private JPanel createPersonCostPanel(Cost cost, Person person, Cost personCost, boolean personHasCosts) {

        Insets insets2 = new Insets(0, 0, 0, 10);
        Insets insets3 = new Insets(0, 10, 0, 5);

        if (!personHasCosts) {
            person.addCost(new Cost(personCost.getId(), personCost.getName(), personCost.getValue(), personCost.getQty()));
        }

        JCostLabel costLabel = new JCostLabel(String.valueOf(personCost.getValue()) + " zl");

        JPanel contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(
                BorderFactory.createLineBorder(new Color(1, 1, 1), 1));

        contentPane.setLayout(gridBagLayout);

        // ADD person cost
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets3;
        contentPane.add(new JLabel("Qty:"), gbc);

        // ADD person QtyLabel
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets2;
        final JQtyTextField qtyTextField;
        qtyTextField = new JQtyTextField(String.valueOf(personCost.getQty()));
        qtyTextField.setColumns(4);
        qtyTextField.setMinimumSize(qtyTextField.getPreferredSize());
        qtyTextField.setCostLabel(costLabel);
        qtyTextField.setCost(cost);
        qtyTextField.setPerson(person);
        qtyTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            public void check() {
                try {
                    Double changedValue = Double.parseDouble(qtyTextField.getText());
                    System.out.println("1: " + qtyTextField.getCost().getValue() * changedValue);
                    System.out.println("2: " + changedValue);
                    System.out.println("3: " + qtyTextField.getText());
                    if (Double.compare(changedValue, Double.valueOf(0.0)) < 0.0) {
                        qtyTextField.getCostLabel().setText("???");
                        updateTotalValues(qtyTextField.getPerson(), true);
                        return;
                    }
                    qtyTextField.getCostLabel().setText(Double.valueOf(Math.round((qtyTextField.getCost().getValue() * changedValue / qtyTextField.getCost().getQty()) * 100.0) / 100.0).toString() + " zl");
                    qtyTextField.getPerson().modifyCost(qtyTextField.getCost().getName(), Double.valueOf(Math.round((qtyTextField.getCost().getValue() * changedValue / qtyTextField.getCost().getQty()) * 100.0) / 100.0));
                    qtyTextField.getPerson().modifyQty(qtyTextField.getCost().getName(), Double.valueOf(qtyTextField.getText()));
                    updateTotalValues(qtyTextField.getPerson(), false);
                } catch (NumberFormatException e) {
                    qtyTextField.getCostLabel().setText("???");
                    updateTotalValues(qtyTextField.getPerson(), true);
                }
            }

        });
        contentPane.add(qtyTextField, gbc);

        // ADD person qtyTextField
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets2;

        contentPane.add(costLabel, gbc);

        return contentPane;
    }

    private void addPersonCostPanel(JPanel personCostPanel, int personCounter) {
        // Add person cost panel
        gbc.gridx = 2 + personCounter;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        gbc.ipady = 5;

        mainPanel.add(personCostPanel, gbc);
        gbc.ipady = 0;
    }

    private void addRemoveRowButton() {
        removeButton = new JRemoveButton("Remove cost");
        removeButton.setRow(currentRow);
        removeButtons.add(removeButton);
        final int rowToRemove = currentRow;
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRow(rowToRemove + 1); // there is also sep=# line
            }
        });

        gbc.gridx = 3 + PeopleManager.getPeopleCount();
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        mainPanel.add(removeButton, gbc);
    }

    private void removeRow(int rowToRemove) {
        try {
            String prefix = "accountingTemp";
            String suffix = ".csv";
            File tempFile = File.createTempFile(prefix, suffix);
            String tempPath = tempFile.getPath();
            System.out.println("Temp File: " + tempPath);
            System.out.println("RowToRemove: " + rowToRemove);
            createCSVFile(tempFile.getPath());

            File tempFile2 = File.createTempFile(prefix + "2", suffix);

            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile2));

            String currentLine;
            int counterLines = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (counterLines++ == rowToRemove) {
                    continue;
                }
                System.out.println("Current line: " + currentLine);
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            //boolean successful = tempFile2.renameTo(tempFile);
            //System.out.println("Renamed + " + successful);

            System.out.println("tempFile2: " + tempFile2.getPath());
            System.out.println("tempFile2 exists: " + tempFile2.exists());
            readCSVFile(tempFile2.getPath());

            System.out.println("Read");

            tempFile.delete();
            tempFile2.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MainWindow.this, "Could not delete.");
            return;
        }
    }

    private void addCostRow(Cost cost, Cost personCost, ArrayList<JPanel> personCostsPanels) {
        CostManager.addCost(cost);
        currentRow++;

        removeButtons();

        // ADD cost name label
        addCostNameLabel(cost.getName());

        // ADD cost value label
        addCostValueLabel(cost.getValue().toString());

        // ADD cost qty label
        addCostQtyLabel(cost.getQty().toString());

        int personCounter = 0;

        if (personCostsPanels == null) {
            personCostsPanels = new ArrayList<>();

            for (Person person : PeopleManager.getPeople()) {
                personCostsPanels.add(createPersonCostPanel(cost, person, personCost, false));
            }
        }

        for (JPanel pcp : personCostsPanels) {
            addPersonCostPanel(pcp, ++personCounter);
        }

        // return to values
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;

        setTotalLabels();
        setButtons();
        addRemoveRowButton();

        updateTotalValues();

        mainPanel.revalidate();

        if (getBounds().height < 500) {
            System.out.println(getBounds().width);
            System.out.println(getBounds().height);
            pack();
        }
        setLocationRelativeTo(null);
    }

    private void updateTotalValues() {
        for (Person person : PeopleManager.getPeople()) {
            System.out.println("Person: " + person.getName());
            for (JCostLabel totalLabels : totalCostPeopleLabels) {

                if (totalLabels.getPerson() == person) {
                    if (totalLabels.getText().toString().equals("???")) {
                        continue;
                    }
                    Double totalPersonCost = 0.0;
                    for (Cost costsPerson : person.getAllCosts()) {
                        System.out.println("Cost: " + costsPerson.getValue());
                        totalPersonCost += costsPerson.getValue();
                    }
                    totalLabels.setText(totalPersonCost.toString() + " zl");
                    break;
                }

            }
        }
    }

    private void updateTotalValues(Person person, boolean undefined) {
        for (JCostLabel totalLabels : totalCostPeopleLabels) {
            if (totalLabels.getPerson() == person) {
                if (undefined) {
                    totalLabels.setText("???");
                    break;
                }
                Double totalPersonCost = 0.0;
                for (Cost costsPerson : person.getAllCosts()) {
                    totalPersonCost += costsPerson.getValue();
                }
                totalLabels.setText(totalPersonCost.toString() + " zl");
                break;
            }
        }
    }

    private void setInitLabels() {
        int x = 0;
        int y = 0;

        // Remove all
        mainPanel.removeAll();

        // ADD cost name label
        gbc.gridx = x++;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(costNameLabel, gbc);

        // ADD cost value label
        gbc.gridx = x++;
        gbc.insets = insets;

        mainPanel.add(costValueLabel, gbc);

        // ADD cost qty label
        gbc.gridx = x++;
        gbc.insets = insets;

        mainPanel.add(costQtyLabel, gbc);

        currentColumn = x;
    }

    private void addPersonLabel(String personName) {
        gbc.gridx = currentColumn;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;

        mainPanel.add(new JLabel(personName), gbc);
    }

    private void addPersonColumn(Person person) {

        initScreen = false;
        PeopleManager.addPerson(person);

        if (currentColumn == 0) {
            setInitLabels();
        }
        removeButtons();
        removeRemoveCostsButtons();

        // ADD person label
        addPersonLabel(person.getName());

        Cost personCost;
        int rowsCount = 0;

        for (Cost cost : CostManager.getCosts()) {
            rowsCount++;
            if (person.getCost(cost.getName()) == null) {

                personCost = new Cost(0, cost.getName(), 0.0d, 0.0d);
                JPanel contentPane = createPersonCostPanel(cost, person, personCost, false);

                // ADD contentPane
                gbc.gridx = 2 + PeopleManager.getPeopleCount();
                gbc.gridy = rowsCount;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.insets = insets;
                gbc.ipady = 5;

                mainPanel.add(contentPane, gbc);
                gbc.ipady = 0;

                // Add remove row button
                removeButton = new JRemoveButton("Remove cost");
                removeButton.setRow(rowsCount);
                removeButtons.add(removeButton);
                final int rowToRemove = rowsCount;
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeRow(rowToRemove + 1); // there is also sep=# line
                    }
                });

                gbc.gridx = 3 + PeopleManager.getPeopleCount();
                gbc.gridy = rowsCount;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.insets = insets;
                gbc.ipady = 0;

                mainPanel.add(removeButton, gbc);
            }
            // return values
            gbc.ipady = 0;
            gbc.fill = GridBagConstraints.NONE;
        }

        if (currentRow != 0) {
            setTotalLabels();
        }
        currentColumn++;

        setButtons();

        mainPanel.revalidate();
        if (getBounds().height < 500) {
            System.out.println(getBounds().width);
            System.out.println(getBounds().height);
            pack();
        }
        setLocationRelativeTo(null);
    }

    private void setAddPersonButton() {
        if (initScreen) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.insets = insets;

            mainPanel.add(addPersonButton, gbc);
            return;
        }

        gbc.gridx = 3 + PeopleManager.getPeopleCount();
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        mainPanel.add(addPersonButton, gbc);
    }

    private void setAddCostButton() {
        if (initScreen) {
            return;
        }
        gbc.gridx = 3 + PeopleManager.getPeopleCount();
        gbc.gridy = currentRow + 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        mainPanel.add(addCostButton, gbc);
    }

    private void setSaveCSVButton() {
        if (initScreen || currentRow == 0) {
            return;
        }
        gbc.gridx = 0;
        gbc.gridy = currentRow + 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = insets;
        mainPanel.add(saveCSVButton, gbc);
    }

    private void setReadCSVButton() {
        if (initScreen) {
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.insets = insets;

            mainPanel.add(readCSVButton, gbc);
        }
    }

    private void initAddPersonButton() {
        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPersonDialog addPersonDialog = new AddPersonDialog();
                addPersonDialog.show();
                addPersonDialog.setOnOkButtonClickedListener(new AddPersonDialog.OnOkButtonClickedListener() {
                    @Override
                    public void okButtonClicked(String text) {
                        System.out.println(text);
                        addPersonColumn(new Person(text));
                    }
                });
            }
        });
    }

    public void initAddCostButton() {
        addCostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCostDialog addCostDialog = new AddCostDialog();
                addCostDialog.show();
                addCostDialog.setOnOkButtonClickedListener(new AddCostDialog.OnOkButtonClickedListener() {
                    @Override
                    public void okButtonClicked(String text, Double value, Double qty) {
                        System.out.println(text);
                        System.out.println(value);

                        int peopleSize = PeopleManager.getPeopleCount();
                        Cost totalCost = new Cost(currentRow, text, value, qty);
                        Cost personCost = new Cost(0, totalCost.getName(), Math.round((totalCost.getValue() / peopleSize) * 100.0) / 100.0, Math.round((totalCost.getQty() / peopleSize) * 100.0) / 100.0);

                        addCostRow(totalCost, personCost, null);
                    }
                });
            }
        });
    }

    public void initSaveCSVButton() {
        saveCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                        String prefix = fileChooser.getSelectedFile().getPath();
                        System.out.println("1: " + prefix);
                        System.out.println("2: " + prefix.substring(prefix.length() - 4, prefix.length() - 1));
                        if (!prefix.endsWith(".csv")) {
                            prefix = prefix + ".csv";
                        }
                        createCSVFile(prefix);
                    }
                } catch (IOException ee) {
                    JOptionPane.showMessageDialog(MainWindow.this, "SomethingWentWrong!");
                }
            }
        });
    }

    private void initReadCSVButton() {
        readCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
                    fileChooser.setFileFilter(filter);
                    if (fileChooser.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getPath();
                        readCSVFile(path);
                    }
                } catch (IOException ee) {
                    JOptionPane.showMessageDialog(MainWindow.this, "SomethingWentWrong!");
                }
            }
        });
    }

    private void createCSVFile(String path) throws IOException {
        try (Writer fw = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            System.out.println("Prefix + suffix " + path);
            StringBuilder sb = new StringBuilder();
            sb.append("sep=#\n");
            sb.append("CostTitle#CostValue#CostQty");
            for (Person person : PeopleManager.getPeople()) {
                sb.append("#");
                sb.append("Qty#");
                sb.append(person.getName());
            }
            sb.append("\n");
            for (Cost cost : CostManager.getCosts()) {
                sb.append(cost.getName() + "#" + cost.getValue() + " zl#" + cost.getQty());
                for (Person person : PeopleManager.getPeople()) {
                    sb.append("#");
                    sb.append(person.getCost(cost.getName()).getQty() + "#");
                    sb.append(person.getCost(cost.getName()).getValue() + " zl");
                }
                sb.append("\n");
            }
            sb.append(totalLabel.getText() + "#" + totalCostLabel.getText() + "#");
            for (JLabel totalCosts : totalCostPeopleLabels) {
                sb.append("##");
                sb.append(totalCosts.getText());
            }
            fw.write(sb.toString());
            fw.close();
        }
    }

    private void readCSVFile(String path) throws IOException {
        java.util.List<String> lines = Files.readAllLines(Paths.get(path));

        clearAllComp();

        Person newPerson = null;
        Cost newCost;
        Cost personCost;
        String costName;
        Double costValue;
        Double costQty;

        boolean firstMainLine = true;
        int counter = 0;
        ArrayList<JPanel> personCostsPanels;

        for (String line : lines) {
            System.out.println("line: " + line);
            if (++counter == lines.size()) {
                break;
            }
            System.out.println(line);
            if (line.startsWith("sep=#")) {
                continue;
            }
            String[] lineCSVValues = line.split("#");
            if (firstMainLine) {
                for (int i = 4; i < lineCSVValues.length; i = i + 2) {
                    newPerson = new Person(lineCSVValues[i]);
                    addPersonColumn(newPerson);
                }
                firstMainLine = false;
                continue;
            }
            try {
                costName = lineCSVValues[0].trim();
                costValue = Double.valueOf(lineCSVValues[1].replace("zl", "").trim());
                costQty = Double.valueOf(lineCSVValues[2].replace("zl", "").trim());

                newCost = new Cost(currentRow, costName, costValue, costQty);

                personCostsPanels = new ArrayList<JPanel>();

                int start = 0;
                for (int i = 3; i < lineCSVValues.length; i = i + 2) {
                    personCost = new Cost(currentRow, costName, Double.valueOf(lineCSVValues[i + 1].replace("zl", "").trim()), Double.valueOf(lineCSVValues[i]));

                    Person pperson = PeopleManager.getPerson(start++);
                    pperson.addCost(personCost);

                    personCostsPanels.add(createPersonCostPanel(newCost, newPerson, personCost, true));
                }
                addCostRow(newCost, null, personCostsPanels);

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(MainWindow.this, "Could not parse CostValue or CostQty!");
                return;
            } catch (ArrayIndexOutOfBoundsException aioob) {
                aioob.printStackTrace();
                JOptionPane.showMessageDialog(MainWindow.this, "Could not parse CSV file!!");
                return;
            }
        }
    }
}
