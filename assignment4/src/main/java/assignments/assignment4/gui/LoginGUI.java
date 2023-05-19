package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // init gui
        GridBagConstraints grid = new GridBagConstraints();
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.NONE;

        //set semua label, field, dan button. Add nilai gridy setelah menambahkan ke panel
        idLabel = new JLabel("Masukan ID Anda:");
        grid.gridy = 0;
        mainPanel.add(idLabel, grid);

        idTextField = new JTextField();
        idTextField.setPreferredSize(new Dimension(650, 20)); //set size field
        grid.gridy = 1;
        mainPanel.add(idTextField, grid);

        passwordLabel = new JLabel("Masukan password Anda:");
        grid.gridy = 2;
        mainPanel.add(passwordLabel, grid);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(650, 20)); //set size field
        grid.gridy = 3;
        mainPanel.add(passwordField, grid);

        grid.anchor = GridBagConstraints.CENTER; //set anchor kembali ke center
        
        loginButton = new JButton("Login");
        grid.gridy = 4;
        mainPanel.add(loginButton, grid);
        loginButton.addActionListener(new ActionListener(){ //add listener login button
            public void actionPerformed(ActionEvent e){
                handleLogin();
            }
        });

        backButton = new JButton("Kembali");
        grid.gridy = 5;
        mainPanel.add(backButton, grid);
        backButton.addActionListener(new ActionListener(){ //add listener back button
            public void actionPerformed(ActionEvent e){
                handleBack();
            }
        });
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        //kembali ke home page dan mengosongkan field
        MainFrame.getInstance().navigateTo("HOME");
        idTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // cek login 
        boolean cekLogin = MainFrame.getInstance().login(idTextField.getText(), new String(passwordField.getPassword()));
        if(cekLogin){
            //menentukan employee atau member yang login
            SystemCLI memberCek = loginManager.getSystem(idTextField.getText());
            if(memberCek instanceof MemberSystem){
                MainFrame.getInstance().navigateTo("MEMBER");
            }else if(memberCek instanceof EmployeeSystem){
                MainFrame.getInstance().navigateTo("EMPLOYEE");
            }
            //set field menjadi kosong
            idTextField.setText("");
            passwordField.setText("");
        }else{
            //saat ok dipencet field akan kosong
            int res = JOptionPane.showOptionDialog(this, "ID atau password invalid.", "Invalid ID or Password", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE, null, null, null);
            if (res == 0){
                idTextField.setText("");
                passwordField.setText("");
            }
        }
    }
}
