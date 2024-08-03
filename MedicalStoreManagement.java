import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicalStoreManagement extends JFrame {
    private static final String FILE_NAME = "credentials.dat";
    private static final String MEDICAL_FILE_NAME = "medical_list.dat";
    private List<Medical> medicalList = new ArrayList<>();
    private List<Bill> billList = new ArrayList<>();
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField medIdField, medNameField, unitCostField, sellCostField, medQuantityField, expDateField, manuDateField;
    private JTextField billIdField, billMedIdField, billQuantityField, billUserNameField;
    private JTextArea stockArea, salesArea;

    public MedicalStoreManagement() {
        initUI();
        init();
        loadMedicalData();
    }

    private void initUI() {
        setTitle("Medical Store Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        JPanel mainPanel = new JPanel(new CardLayout());
        add(mainPanel);

        JPanel loginPanel = createLoginPanel(mainPanel);
        mainPanel.add(loginPanel, "login");

        JPanel menuPanel = createMenuPanel(mainPanel);
        mainPanel.add(menuPanel, "menu");

        JPanel medicinePanel = createMedicinePanel(mainPanel);
        mainPanel.add(medicinePanel, "medicine");

        JPanel billingPanel = createBillingPanel(mainPanel);
        mainPanel.add(billingPanel, "billing");

        JPanel salesPanel = createSalesPanel(mainPanel);
        mainPanel.add(salesPanel, "sales");

        JPanel stockPanel = createStockPanel(mainPanel);
        mainPanel.add(stockPanel, "stock");

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "login");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveMedicalData();
                System.exit(0);
            }
        });
    }

    private JPanel createLoginPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setBackground(new Color(173, 216, 230));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLACK);
        panel.add(userLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 179, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> login(mainPanel));
        panel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        return panel;
    }

    private JPanel createMenuPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setBackground(new Color(255, 228, 196));

        JButton medicineButton = new JButton("Medicine purchase");
        medicineButton.setBackground(new Color(100, 149, 237));
        medicineButton.setForeground(Color.WHITE);
        medicineButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "medicine"));
        panel.add(medicineButton);

        JButton billingButton = new JButton("Billing");
        billingButton.setBackground(new Color(100, 149, 237));
        billingButton.setForeground(Color.WHITE);
        billingButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "billing"));
        panel.add(billingButton);

        JButton salesButton = new JButton("Sales");
        salesButton.setBackground(new Color(100, 149, 237));
        salesButton.setForeground(Color.WHITE);
        salesButton.addActionListener(e -> {
            showSales();
            ((CardLayout) mainPanel.getLayout()).show(mainPanel, "sales");
        });
        panel.add(salesButton);

        JButton stockButton = new JButton("Stock");
        stockButton.setBackground(new Color(100, 149, 237));
        stockButton.setForeground(Color.WHITE);
        stockButton.addActionListener(e -> {
            showStock();
            ((CardLayout) mainPanel.getLayout()).show(mainPanel, "stock");
        });
        panel.add(stockButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "login"));
        panel.add(logoutButton);

        return panel;
    }


    private JPanel createMedicinePanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 0));
        panel.setBackground(new Color(245, 245, 220));

        JLabel idLabel = new JLabel("ID:");
        idLabel.setForeground(Color.BLACK);
        panel.add(idLabel);
        medIdField = new JTextField();
        panel.add(medIdField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.BLACK);
        panel.add(nameLabel);
        medNameField = new JTextField();
        panel.add(medNameField);

        JLabel unitCostLabel = new JLabel("Unit Cost:");
        unitCostLabel.setForeground(Color.BLACK);
        panel.add(unitCostLabel);
        unitCostField = new JTextField();
        panel.add(unitCostField);

        JLabel sellCostLabel = new JLabel("Sell Cost:");
        sellCostLabel.setForeground(Color.BLACK);
        panel.add(sellCostLabel);
        sellCostField = new JTextField();
        panel.add(sellCostField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.BLACK);
        panel.add(quantityLabel);
        medQuantityField = new JTextField();
        panel.add(medQuantityField);

        JLabel manuDateLabel = new JLabel("Manufacture Date:");
        manuDateLabel.setForeground(Color.BLACK);
        panel.add(manuDateLabel);
        manuDateField = new JTextField();
        panel.add(manuDateField);
        
        JLabel expDateLabel = new JLabel("Expiry Date:");
        expDateLabel.setForeground(Color.BLACK);
        panel.add(expDateLabel);
        expDateField = new JTextField();
        panel.add(expDateField);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(60, 179, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> addMedicine());
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "menu"));
        panel.add(backButton);

        return panel;
    }


    private JPanel createBillingPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(255, 239, 213));

        JLabel billIdLabel = new JLabel("Bill No:");
        billIdLabel.setForeground(Color.BLACK);
        panel.add(billIdLabel);
        billIdField = new JTextField();
        panel.add(billIdField);

        JLabel userLabel = new JLabel("User Name:");
        userLabel.setForeground(Color.BLACK);
        panel.add(userLabel);
        billUserNameField = new JTextField();
        panel.add(billUserNameField);

        JLabel medLabel = new JLabel("Medicine ID:");
        medLabel.setForeground(Color.BLACK);
        panel.add(medLabel);
        billMedIdField = new JTextField();
        panel.add(billMedIdField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.BLACK);
        panel.add(quantityLabel);
        billQuantityField = new JTextField();
        panel.add(billQuantityField);

        JButton sellButton = new JButton("Sell");
        sellButton.setBackground(new Color(60, 179, 113));
        sellButton.setForeground(Color.WHITE);
        sellButton.addActionListener(e -> sellMedicine());
        panel.add(sellButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "menu"));
        panel.add(backButton);

        return panel;
    }

    private JPanel createSalesPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(224, 255, 255));

        salesArea = new JTextArea();
        salesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(salesArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "menu"));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStockPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(245, 245, 220));

        stockArea = new JTextArea();
        stockArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(stockArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> ((CardLayout) mainPanel.getLayout()).show(mainPanel, "menu"));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private void login(JPanel mainPanel) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Credentials credentials = (Credentials) ois.readObject();
            if (credentials.getUsername().equals(username) && credentials.checkPassword(password)) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "menu");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while logging in", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMedicine() {
        String id = medIdField.getText();
        String name = medNameField.getText();
        double unitCost = Double.parseDouble(unitCostField.getText());
        double sellCost = Double.parseDouble(sellCostField.getText());
        int quantity = Integer.parseInt(medQuantityField.getText());
        String manuDate = manuDateField.getText();
        String expDate = expDateField.getText();

        Medical medical = new Medical(id, name, unitCost, sellCost, quantity, manuDate, expDate);
        medicalList.add(medical);

        medIdField.setText("");
        medNameField.setText("");
        unitCostField.setText("");
        sellCostField.setText("");
        medQuantityField.setText("");
        manuDateField.setText("");
        expDateField.setText("");

        JOptionPane.showMessageDialog(this, "Medicine added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sellMedicine() {
        String billId = billIdField.getText();
        String userName = billUserNameField.getText();
        String medId = billMedIdField.getText();
        int quantity = Integer.parseInt(billQuantityField.getText());

        Medical medical = medicalList.stream().filter(m -> m.getId().equals(medId)).findFirst().orElse(null);

        if (medical != null && medical.getQuantity() >= quantity) {
            medical.setQuantity(medical.getQuantity() - quantity);
            double amount = medical.getSellCost() * quantity;
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            Bill bill = new Bill(billId, userName, medId, medical.getName(), quantity, amount, date);
            billList.add(bill);

            JOptionPane.showMessageDialog(this, "Medicine sold successfully\nBill Details:\n" +
                    "Bill No: " + billId + "\n" +
                    "User Name: " + userName + "\n" +
                    "Medicine ID: " + medId + "\n" +
                    "Medicine Name: " + medical.getName() + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Amount: " + amount + "\n" +
                    "Date: " + date, "Success", JOptionPane.INFORMATION_MESSAGE);

            billIdField.setText("");
            billUserNameField.setText("");
            billMedIdField.setText("");
            billQuantityField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient stock or medicine not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSales() {
        salesArea.setText("");
        for (Bill bill : billList) {
            salesArea.append(bill.toString() + "\n");
        }
    }

    private void showStock() {
        stockArea.setText("");
        for (Medical medical : medicalList) {
            stockArea.append(medical.toString() + "\n");
        }
    }

    private void init() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            Credentials credentials = new Credentials("shalini", "shalini");
            oos.writeObject(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMedicalData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEDICAL_FILE_NAME))) {
            medicalList = (List<Medical>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            
        }
    }

    private void saveMedicalData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEDICAL_FILE_NAME))) {
            oos.writeObject(medicalList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MedicalStoreManagement ex = new MedicalStoreManagement();
            ex.setVisible(true);
        });
    }

    static class Credentials implements Serializable {
        private String username;
        private String password;

        public Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public boolean checkPassword(char[] password) {
            return this.password.equals(new String(password));
        }
    }

    static class Medical implements Serializable {
        private String id;
        private String name;
        private double unitCost;
        private double sellCost;
        private int quantity;
        private String manuDate;
        private String expDate;

        public Medical(String id, String name, double unitCost, double sellCost, int quantity, String manuDate, String expDate) {
            this.id = id;
            this.name = name;
            this.unitCost = unitCost;
            this.sellCost = sellCost;
            this.quantity = quantity;
            this.manuDate = manuDate;
            this.expDate = expDate;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getUnitCost() {
            return unitCost;
        }

        public double getSellCost() {
            return sellCost;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getManuDate() {
            return manuDate;
        }

        public String getExpDate() {
            return expDate;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Unit Cost: " + unitCost + ", Sell Cost: " + sellCost + ", Quantity: " + quantity + ", Manufacture Date: " + manuDate + ", Expiry Date: " + expDate;
        }
    }

    static class Bill implements Serializable {
        private String billId;
        private String userName;
        private String medId;
        private String medName;
        private int quantity;
        private double amount;
        private String date;

        public Bill(String billId, String userName, String medId, String medName, int quantity, double amount, String date) {
            this.billId = billId;
            this.userName = userName;
            this.medId = medId;
            this.medName = medName;
            this.quantity = quantity;
            this.amount = amount;
            this.date = date;
        }

        @Override
        public String toString() {
            return "Bill No: " + billId + ", User Name: " + userName + ", Medicine ID: " + medId + ", Medicine Name: " + medName + ", Quantity: " + quantity + ", Amount: " + amount + ", Date: " + date;
        }
    }
}

