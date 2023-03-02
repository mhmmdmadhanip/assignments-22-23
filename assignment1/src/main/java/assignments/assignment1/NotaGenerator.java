package assignments.assignment1;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
            //membuat variabel baru untuk memulai program dengan while loop
        boolean mulai = true;
        
        //memulai program
        while(mulai){
            //memanggil function untuk menampilkan pesan selamat datang dan pilihan
            printMenu();
            System.out.print("Pilihan: ");
            //menginitialize inputPilihan di luar try and catch
            String inputPilihan;
            //menggunakan try and catch untuk memvalidasi input di inputPilihan
            try{
            inputPilihan = input.nextLine();
            System.out.println("================================");
            if(inputPilihan.equals("1")){//Apabila inputPilihan == 2 maka program akan menjalankan generateId
                String nameShow =firstName();
                String inputNomor = nomorHp();
                System.out.println("ID Anda: "+generateId(nameShow, inputNomor));

            }else if(inputPilihan.equals("2")){//Apabila inputPilihan == 2 maka program akan menjalankan generateNota
                String nameShow = firstName();
                String inputNomor = nomorHp();
                System.out.println("Masukkan tanggal terima:");
                String inputDate = input.next();
                String inputPaket = cekPaket();
                int inputBerat = cekBerat();
                System.out.println(generateNota(generateId(nameShow, inputNomor), inputPaket, inputBerat, inputDate));

            }else if(inputPilihan.equals("0")){//Apabila inputPilihan == 0 maka program akan berhenti
                mulai = false;
                System.out.println("Terima kasih telah menggunakan NotaGenerator");
            }else{//Apabila input berbentuk integer namun bukan 0, 1, atau 2 maka kondisi ini akan dijalankan
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
            }
            //Apabila input yang dimasukkan di dalam inputPilihan bukan integer maka kondisi ini akan dijalankan
            catch(Exception MismatchInputException){
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                input.nextLine();//menggunakan input.nextLine agar loop tetap berjalan setelah catch
            }            
        }
        
    }

    /**
     * M--ethod untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    
    //Method untuk meminta input nama dan mereturn kata pertama dari nama yang diinput
    public static String firstName(){
        System.out.println("Masukkan nama Anda:");
        String inputNama = input.nextLine();
        int spaceIndex = inputNama.indexOf(" "); //mencari index spasi
        String firstName ="";
        if(spaceIndex != -1){//apabila input terdapat spasi maka inputNama akan diambil kata depannya saja
            firstName = inputNama.substring(0,spaceIndex);
        }else{ //Apabila tidak ada spasi maka akan mereturn inputNama
            firstName = inputNama;
        }
        return firstName;
    }

    //Methoduntuk memavalidasi nomor HP
    public static String nomorHp(){
        System.out.println("Masukkan nomor handphone Anda:");
        boolean numCheck = true;
        String inputNomor = "";
        while(numCheck){
            inputNomor = input.nextLine();
            byte[] cekNomor = inputNomor.getBytes(StandardCharsets.US_ASCII);//Mengubah inputNomor ke dalam bentuk Ascii
            for(int i = 0; i < inputNomor.length();i ++){
                if(cekNomor[i] < 48 || cekNomor[i] > 57){//Apabila bukan angka 0-9 maka for loop kaan dibreak dan meminta input kembali
                    System.out.println("Nomor hp hanya menerima digit");
                    break;
                }
                //Apabila semua digit berbentuk angka maka numCheck akan menjadi false agar tidak melakukan loop kembali 
                if(i == inputNomor.length()-1 && (cekNomor[i] >= 48 || cekNomor[i] <= 57)){
                    numCheck = false;
                }else{
                    continue;
                }
            }
        }
        return inputNomor;
    }

    //Method untuk memvalidasi tipe paket yang ingin digunakan
    public static String cekPaket(){
        System.out.println("Masukkan paket laundry:");
        String inputPaket = input.next();
        boolean cekPaket;
        //Apabila inputPaket tidak sesuai dengan yang diminta maka akan masuk ke dalam while loop
        if(inputPaket.equalsIgnoreCase("express") ||
            inputPaket.equalsIgnoreCase("fast") ||
            inputPaket.equalsIgnoreCase("reguler")){
            cekPaket = false;
            }else{
            cekPaket = true;
            }
        while(cekPaket){
            //Apabila inputPaket == ? maka akan menampilkan tipe paket 
            if(inputPaket.equalsIgnoreCase("?")){
                showPaket();
                System.out.println("Masukkan paket laundry:");
                inputPaket = input.next();
            }else if(inputPaket.equalsIgnoreCase("express") ||
            inputPaket.equalsIgnoreCase("fast") ||
            inputPaket.equalsIgnoreCase("reguler")){//Apabila input sudah benar maka program akan keluar dari while loop
                cekPaket = false;
            }else{//Apabila paket tidak diketahui maka kondisi ini akan dijalankan
                System.out.println("Paket "+inputPaket+" tidak diketahui");
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                System.out.println("Masukkan paket laundry:");
                inputPaket = input.next();
            }
            }
        return inputPaket;
    }

    //Method untuk validasi berat cucian
    public static int cekBerat(){
        System.out.println("Masukkan berat cucian Anda [Kg]:");
        boolean cekBerat = true;
        int inputBerat = 0;
        //Menggunakan while loop dan try catch untuk memvalidasi
        while(cekBerat){
            try{
            inputBerat = input.nextInt();
            //Apabila inputBerat sudah valid maka program akan keluar dari while loop
            if(inputBerat > 0){
                cekBerat = false;
                }else{
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    }
                }
            //Apabila input tidak bertipe integer maka kondisi ini akan dijalankan
            catch(Exception MismatchInputException){
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                }
            input.nextLine();//Input.nextLine digunakan agar while loop tetap berlanjut
        }
        return inputBerat;
    }

    //Method untuk memberikan return ID
    public static String generateId(String nama, String nomorHP){
        int id = 7;
        nama = nama.toLowerCase(); //Mengubah inputNama menjadi lower case
        //Mengubah inputNama dan inputNomor ke dalam bentuk Ascii
        byte[] asciiName = nama.getBytes(StandardCharsets.US_ASCII);
        byte[] asciiNumber = nomorHP.getBytes(StandardCharsets.US_ASCII);
        //For loop untuk mengubah nama menjadi id
        for(int i = 0; i < nama.length();i ++){
            if(asciiName[i] == 32){//Apabila ada spasi maka program loop akan dibreak
                break;
            }else if(asciiName[i] < 97 || asciiName[i] > 122){//Apabila nama dalam bentuk simbol maka kondisi ini akan dijalankan
                asciiName[i] = 7;
            }else if(asciiName[i] > 47 && asciiName[i] <58 ){//Apabila nama dalam bentuk angka maka kondisi ini akan dijalankan
                asciiName[i] -= 48;
            }else{//Apabila nama berbentuk huruf maka kondisi ini akan dijalankan
                asciiName[i]-=96;
            }
            id += asciiName[i]; //Menjumlahkan semua huruf untuk menjadi ID
        }
        //For loop untuk mengubah angka menjadi id
        for(int i = 0; i < nomorHP.length();i ++){
            if(asciiNumber[i] < 48 || asciiNumber[i] > 57){//Apabila nomor HP dalam bentuk simbol maka ini akan dijlanakn
                asciiNumber[i] = 7;
            }else{//Apabila nomor HP dalam bentuk angka maka kondisi ini akan dijalankan
                asciiNumber[i] -= 48;
            }
            id += asciiNumber[i]; //Menjumlahkan semua angka untuk menjadi ID
        }
        String strId = String.valueOf(id); 
        if(id < 10){ //Apabila id kurang dari 1 digit maka akan ditambahkan 0
            strId = "0"  + id;
        }
        //Mengecek kembali apakah sudah tidak ada spasi dalam nama yang akan dijadikan output
        int indexSpace = nama.indexOf(" "); 
        String finalName = "" ;
        if(indexSpace != -1){
        finalName = nama.substring(0, indexSpace);
        }else{
            finalName = nama;
        }
        int idLength = strId.length();
        String result = finalName.toUpperCase() + "-" + nomorHP + "-" + strId.substring(idLength-2);//Membuat variabel untuk direturn
        return result;
    }

    //Method untuk menggenerate Nota
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        //Membuat format tanggal terima dan tanggal selesai menjadi "dd/mm/yyyy"
        DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkFormat = LocalDate.parse(tanggalTerima, formatDay);

        //Apabila berat cucian kurang dari 2 maka akan dibulatkan menjadi 2 
        if(berat < 2){
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }
        int hargaPaket = 0;
        String strSelesai = "";
        //Menyesuaikan harga dan lama hari selesai sesuai dengan paket yang dipilih
        if(paket.equalsIgnoreCase("express")){
            hargaPaket = 12000;
            LocalDate tanggalSelesai = checkFormat.plusDays(1);
            strSelesai = tanggalSelesai.format(formatDay);
        }else if(paket.equalsIgnoreCase("fast")){
            hargaPaket = 10000;
            LocalDate tanggalSelesai = checkFormat.plusDays(2);
            strSelesai = tanggalSelesai.format(formatDay);
        }else if(paket.equalsIgnoreCase("reguler")){
            hargaPaket = 7000;
            LocalDate tanggalSelesai = checkFormat.plusDays(3);
            strSelesai = tanggalSelesai.format(formatDay);
        }
        int totalHarga = berat*hargaPaket;//Menghitung total harga
        System.out.println("Nota Laundry");
        //Membuat String perbaris
        String idShow = "ID    : "+id+"\n";
        String paketShow = "Paket : "+paket+"\n";
        String hargaShow = "Harga :\n"+ berat+" kg x "+hargaPaket+" = "+totalHarga +"\n";
        String terimaDate = "Tanggal Terima  : "+tanggalTerima+"\n";
        String selesaiDate = "Tanggal Selesai : "+ strSelesai;
        //Menyatukan semua String yang dibuat untuk direturn
        String finalReturn = idShow+paketShow+hargaShow+terimaDate+selesaiDate;
        return finalReturn;
    }
}
