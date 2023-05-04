package assignments.assignment3.user.menu;
import java.util.Arrays;

import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {
    protected int idNota = 0;
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        if(choice == 1){
            doLaundry();
        }else if(choice ==2){
            displayNota();
        }else if(choice == 3){
            logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        memberList = Arrays.copyOf(memberList, memberList.length+1);
        memberList[memberList.length-1] = member;
    }


    private void doLaundry (){
        String paket = showPaket();
        int berat = NotaGenerator.cekBerat();
        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\nHanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String setrika = super.in.nextLine();
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\nCuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String antarLaundry = super.in.nextLine();
        System.out.println("Nota berhasil dibuat!\n");
        Nota notaLaundry = new Nota(super.loginMember, berat, paket, fmt.format(cal.getTime()));
        super.loginMember.addNota(notaLaundry);
        notaLaundry.setSisaHariPengerjaan(paket);
        notaLaundry.setIdNota(idNota);
        idNota++;
        LaundryService cuciService = new CuciService();
        notaLaundry.addService(cuciService);
        if(!setrika.equals("x")){
            LaundryService setrikaService = new SetrikaService();
            notaLaundry.addService(setrikaService);
        }
        if(!antarLaundry.equals("x")){
            LaundryService antarService = new AntarService();
            notaLaundry.addService(antarService);
        }
        NotaManager.addNota(notaLaundry);
    }

    private String showPaket() {
        System.out.println("Masukan paket laundry:");
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
        String inputPaket = super.in.nextLine();
        boolean cekPaket = true;
        if(inputPaket.equalsIgnoreCase("express") ||
            inputPaket.equalsIgnoreCase("fast") ||
            inputPaket.equalsIgnoreCase("reguler")){
            cekPaket = false;
            }
        while(cekPaket){
            //Apabila inputPaket == ? maka akan menampilkan tipe paket 
            if(inputPaket.equalsIgnoreCase("?")){
                System.out.println("Masukkan paket laundry:");
                System.out.println("+-------------Paket-------------+");
                System.out.println("| Express | 1 Hari | 12000 / Kg |");
                System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
                System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
                System.out.println("+-------------------------------+");
                inputPaket = super.in.nextLine();
            }else if(inputPaket.equalsIgnoreCase("express") ||
            inputPaket.equalsIgnoreCase("fast") ||
            inputPaket.equalsIgnoreCase("reguler")){//Apabila input sudah benar maka program akan keluar dari while loop
                cekPaket = false;
            }else{//Apabila paket tidak diketahui maka kondisi ini akan dijalankan
                System.out.println("Paket "+inputPaket+" tidak diketahui");
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                System.out.println("Masukkan paket laundry:");
                inputPaket = super.in.nextLine();
            }
            }
        return inputPaket;
    }

    private void displayNota(){
        for (int i = 0; i < super.loginMember.getNotaList().length; i++) {
            System.out.println(super.loginMember.getNotaList()[i]+"\n");
        }
    }
}