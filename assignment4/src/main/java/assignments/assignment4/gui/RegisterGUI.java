package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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
        // init gbc 
        GridBagConstraints grid = new GridBagConstraints();
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.NONE;

        //add semua field, label, button ke main Panel dan mengubah nilai gridy
        nameLabel = new JLabel("Masukan nama Anda:");
        grid.gridy = 0;
        mainPanel.add(nameLabel, grid);

        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(650, 20)); //set size name field
        grid.gridy = 1;
        mainPanel.add(nameTextField, grid);

        phoneLabel = new JLabel("Masukan nomor Handphone Anda:");
        grid.gridy = 2;
        mainPanel.add(phoneLabel, grid);

        phoneTextField = new JTextField();
        phoneTextField.setPreferredSize(new Dimension(650, 20)); //set size nomor field
        grid.gridy = 3;
        mainPanel.add(phoneTextField, grid);

        passwordLabel = new JLabel("Masukan password Anda:");
        grid.gridy = 4;
        mainPanel.add(passwordLabel, grid);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(650, 20)); //set size password field
        grid.gridy = 5;
        mainPanel.add(passwordField, grid);
        
        registerButton = new JButton("Register");
        grid.gridy = 6;
        grid.anchor = GridBagConstraints.CENTER; //mengembalikan anchor ke tengah
        mainPanel.add(registerButton, grid);
        registerButton.addActionListener(new ActionListener(){//add listener register button
            public void actionPerformed(ActionEvent e){
                handleRegister();
            }
        });

        backButton = new JButton("Kembali");
        grid.gridy = 7;
        mainPanel.add(backButton, grid);
        backButton.addActionListener(new ActionListener(){//add listener back button
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
        MainFrame.getInstance().navigateTo("HOME"); //menuju home page
        //set semua field menjadi kosong
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // cek semua field, return agar method berhenti
        if(nameTextField.getText().equals("") ||
         phoneTextField.getText().equals("") || 
         new String(passwordField.getPassword()).equals("")){
             JOptionPane.showMessageDialog(this, "Semua field diatas wajib diisi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //validasi nomor hp, return agar method berhenti
        else if(!phoneTextField.getText().matches("[0-9]+")){
            //saat ok buton dipencet field nomor akan kosong
            int res = JOptionPane.showOptionDialog(this, "Nomor handphone harus berisi angka", "Invalid Phone Number", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE, null, null, null);
            if (res == 0){
                phoneTextField.setText("");
            }
            return;
        }
        //membuat objek member baru
        Member memberBaru = loginManager.register(nameTextField.getText(), phoneTextField.getText(), new String(passwordField.getPassword()));
        if(memberBaru == null){
            //option pane saat gagal membuat member
            JOptionPane.showMessageDialog(this, "User dengan nama "+ nameTextField.getText() + " dan nomor hp "+ phoneTextField.getText()+ " sudah ada!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            handleBack();
        }else{
            //option pane saat berhasil membuat member
            JOptionPane.showMessageDialog(this, "Berhasil membuat user dengan ID " + memberBaru.getId() + "!", "Registration Succesful", JOptionPane.INFORMATION_MESSAGE);
            handleBack();
        }
    }
}
