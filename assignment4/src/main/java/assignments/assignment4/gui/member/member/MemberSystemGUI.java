package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        return new JButton[]{
            new JButton("Saya ingin laundry"),
            new JButton("Lihat detail nota saya")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        // TODO
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a JTextArea
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        
        // Create a JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 300)); // Set the preferred size for JScrollPane
        
        // Add the JScrollPane to the mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Set the text in JTextArea
        String result = "";
        for (Nota nota : loggedInMember.getNotaList()) {
            result += nota + "\n\n\n";
        }
        if(loggedInMember.getNotaList().length == 0){
            textArea.setText("Belum pernah laundry di CuciCuci, hiks :'(");
        }else{
            textArea.setText(result);
        }
        mainPanel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                MainFrame.getInstance().dispose();
            }
        });
        // Show the JOptionPane with the mainPanel
        JOptionPane.showMessageDialog(null, mainPanel, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);

        // Add the mainPanel to the MainFrame
        MainFrame.getInstance().getContentPane().add(mainPanel);

    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        // TODO
        MainFrame.getInstance().navigateTo("CREATE_NOTA");
    }

}
