package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.toNextDay;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

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
        //init gbc
        GridBagConstraints grid = new GridBagConstraints();
        grid.anchor = GridBagConstraints.NORTH;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.NONE;

        //set semua label dan button. Menambahkan label dan button ke mainpanel dan merubah gridy setiap kali menambahkan sesuatu
        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        grid.gridy = 0;
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); //set font
        mainPanel.add(titleLabel, grid);

        loginButton = new JButton("Login");
        grid.gridy = 1;
        grid.anchor = GridBagConstraints.CENTER; //mengubah anchor ke center
        mainPanel.add(loginButton, grid);
        loginButton.addActionListener(new ActionListener(){ //add action listner untuk login button
            public void actionPerformed(ActionEvent e){
                handleToLogin();
            }
        });

        registerButton = new JButton ("Register");
        grid.gridy = 2;
        mainPanel.add(registerButton, grid);
        registerButton.addActionListener(new ActionListener(){ //add action listener untuk register button
            public void actionPerformed(ActionEvent e){
                handleToRegister();
            }
        });

        toNextDayButton = new JButton ("Next Day");
        grid.gridy = 3;
        mainPanel.add(toNextDayButton, grid);

        toNextDayButton.addActionListener(new ActionListener(){ //add action listener untuk to next day
            public void actionPerformed(ActionEvent e){
                handleNextDay();
            }
        });

        dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()));
        grid.gridy = 4;
        grid.anchor = GridBagConstraints.SOUTH; //set anchor ke south
        mainPanel.add(dateLabel, grid);
        
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo("REGISTER"); //pergi ke register panel
    }
    
    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo("LOGIN"); //pergi ke login panel
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay(); //memanggil method nextday di tp 3
        //set tanggal dan menampilkan option pane
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));
        JOptionPane.showMessageDialog(null, "Kamu tidur hari ini... zzz..", "This is Prince Paul's Bubble Party's abilitiy!", JOptionPane.INFORMATION_MESSAGE);
    }
}
