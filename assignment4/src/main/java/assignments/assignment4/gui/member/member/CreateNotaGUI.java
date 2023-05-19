package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

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
        // TODO
        GridBagConstraints grid = new GridBagConstraints();
        grid.anchor = GridBagConstraints.NORTHWEST;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.NONE;

        paketLabel = new JLabel("Paket Laundry:");
        grid.gridx = 0;
        grid.gridy = 0;
        mainPanel.add(paketLabel, grid);

        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Reguler"});
        grid.gridx = 1;
        mainPanel.add(paketComboBox, grid);

        showPaketButton = new JButton("Show Paket");
        grid.gridx = 2;
        mainPanel.add(showPaketButton, grid);
        showPaketButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                showPaket();
            }
        });

        beratLabel = new JLabel("Berat Cucian (Kg):");
        grid.gridx = 0;
        grid.gridy = 1;
        mainPanel.add(beratLabel, grid);

        beratTextField = new JTextField();
        beratTextField.setPreferredSize(new Dimension(75, 20));
        grid.gridx = 1;
        mainPanel.add(beratTextField, grid);
        
        setrikaCheckBox = new JCheckBox ("Tambah Setrika Service (1000/kg)");
        setrikaCheckBox.setFocusable(false);
        grid.gridx = 0;
        grid.gridy = 2;
        mainPanel.add(setrikaCheckBox, grid);

        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4kg pertama, kemudian 500/kg)");
        antarCheckBox.setFocusable(false);
        grid.gridy = 3;
        mainPanel.add(antarCheckBox, grid);

        createNotaButton = new JButton("Buat Nota");
        createNotaButton.setPreferredSize(new Dimension(500, 20));
        grid.gridy = 4;
        mainPanel.add(createNotaButton, grid);
        createNotaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createNota();
            }
        });

        backButton = new JButton("Kembali");
        backButton.setPreferredSize(new Dimension(500, 20));
        grid.gridy = 5;
        mainPanel.add(backButton, grid);
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                handleBack();
            }
        });

    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;
        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // cek berat
        if(!beratTextField.getText().matches("\\d+")){
            JOptionPane.showMessageDialog(null, "Berat Cucian harus berisi angka", "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
        }else{
            //mengubah berat ke int
            int berat  = Integer.parseInt(beratTextField.getText());
            if(berat == 0){ //berat bukan bilangan bulat positif, return untuk memberhentikan method
                JOptionPane.showMessageDialog(this, "Berat Cucian harus berisi angka", "Error", JOptionPane.ERROR_MESSAGE);
                beratTextField.setText("");
                return;
            }else if(berat > 0 && berat < 2){
                //berat kurang dari 2
                JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
                berat = 2;
            }
            //buat nota baru dan set attribte
            Nota notaBaru = new Nota(memberSystemGUI.getLoggedInMember(), berat, (String) paketComboBox.getSelectedItem(), fmt.format(cal.getTime()));
            notaBaru.setSisaHariPengerjaan((String)paketComboBox.getSelectedItem());
            //add semua service
            LaundryService cuciService = new CuciService();
            notaBaru.addService(cuciService);
            if(setrikaCheckBox.isSelected()){
                LaundryService setrikaService = new SetrikaService();
                notaBaru.addService(setrikaService);
            }
            if(antarCheckBox.isSelected()){
                LaundryService antarService = new AntarService();
                notaBaru.addService(antarService);
            }
            //add nota ke nota list member dan nota manager
            memberSystemGUI.getLoggedInMember().addNota(notaBaru);
            NotaManager.addNota(notaBaru);

            //set semua field menjadi kosong setelah dipencet ok
            int res = JOptionPane.showOptionDialog(this, "Nota berhasil dibuat!", "Success", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (res == 0){
                beratTextField.setText("");
                setrikaCheckBox.setSelected(false);
                antarCheckBox.setSelected(false);
                paketComboBox.setSelectedItem("Express");
            }
        }
    }
    
    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // menuju member gui dan set semua field menjadi kosong
        MainFrame.getInstance().navigateTo("MEMBER");
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
        paketComboBox.setSelectedItem("Express");
    }
}
