package assignments.assignment2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static assignments.assignment1.NotaGenerator.*;
import assignments.assignment1.NotaGenerator;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static Nota[] notaList;
    private static Member[] memberList;
    private static int idNota;
    private static int bonusCounter;

    public static void main(String[] args) {
        memberList = new Member[0];
        notaList = new Nota[0];
        idNota = 0;
        bonusCounter = 0;
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        String inputNama = NotaGenerator.firstName();
        String inputNomor = NotaGenerator.nomorHp();
        String id = NotaGenerator.generateId(inputNama, inputNomor);
        Member objMember = new Member(inputNama,inputNomor,id, bonusCounter);
        if(memberList.length == 0){
            memberList = Arrays.copyOf(memberList, memberList.length +1);
            memberList[memberList.length -1] = objMember;
            System.out.println("Berhasil membuat member dengan ID "+id+"!");
        }else{
            int indexCek = 0;
            for(int i = 0; i < memberList.length; i++){
                if(!memberList[i].getId().equals(id)){
                    indexCek++;
                }
            }
            if(indexCek == memberList.length){
                memberList = Arrays.copyOf(memberList, memberList.length +1);
                memberList[memberList.length -1] = objMember;

                System.out.println("Berhasil membuat member dengan ID "+id+"!");
            }else{
                System.out.println("Member dengan nama "+inputNama + " dan nomor hp "+inputNomor+" sudah ada!");
            }
        }
    }

    private static void handleGenerateNota() {
        System.out.println("Masukkan ID member:");
        String idMember = input.nextLine();
        boolean idValid = false;
        int indexMember = 0;
        for(int i = 0; i < memberList.length; i++){
            if(memberList[i].getId().equals(idMember)){
                idValid = true;
                indexMember = i;
            }
        }
        if(!idValid){
            System.out.println("Member dengan ID "+idMember+" tidak ditemukan!");
        }else{
            boolean isReady = false;
            String paketLaundry = NotaGenerator.cekPaket();
            int sisaHariPengerjaan = 0;
            if(paketLaundry.equalsIgnoreCase("express")){
                sisaHariPengerjaan = 1;
            }else if(paketLaundry.equalsIgnoreCase("fast")){
                sisaHariPengerjaan = 2;
            }else if(paketLaundry.equalsIgnoreCase("reguler")){
                sisaHariPengerjaan = 3;
            }
            int beratLaundry = NotaGenerator.cekBerat();
            memberList[indexMember].tambahBonus();
            System.out.println("Berhasil menambahkan nota!");
            System.out.println("[ID Nota = "+idNota+"]");
            System.out.println(generateNota(idMember, paketLaundry, beratLaundry, fmt.format(cal.getTime()), memberList[indexMember].getBonusCounter()));
            Nota objNota = new Nota(memberList[indexMember], paketLaundry, beratLaundry, fmt.format(cal.getTime()), idNota, sisaHariPengerjaan, isReady);
            notaList = Arrays.copyOf(notaList, notaList.length+1);
            notaList[notaList.length-1] = objNota;
            idNota++;
        }
    }

    private static void handleListNota() {
        String status;
        System.out.println("Terdaftar "+notaList.length+" nota dalam sistem.");
        for(int i = 0; i < notaList.length; i++){
            if(!notaList[i].getIsReady()){
                status = "Belum bisa diambil :(";
            }else{
                status = "Sudah dapat diambil!";
            }
            System.out.println("- ["+notaList[i].getIdNota()+"] Status      	: "+status);
        }
    }

    private static void handleListUser() {
        System.out.println("Terdaftar "+memberList.length+" member dalam sistem.");
        for(int i = 0; i < memberList.length; i++){
            System.out.println("- "+memberList[i].getId()+" : "+memberList[i].getNama());
        } 
    }

    private static void handleAmbilCucian() {
        Nota[] tempNota = new Nota[notaList.length-1];
        int indexTemp = 0;
        System.out.println("Masukan ID Nota yang akan diambil:");
        String strIdAmbil = input.nextLine();
        while(!strIdAmbil.matches("\\d+")){
            System.out.println("ID nota berbentuk angka!");
            strIdAmbil = input.nextLine();
        }
        int idAmbil = Integer.parseInt(strIdAmbil);
        boolean cekId = false;
        int indexId = 0;
        for(int i = 0; i < notaList.length; i++){
            if(notaList[i].getIdNota() == idAmbil){
                cekId = true;
                indexId = i;
                break;
            }
        }
        if(cekId && notaList[indexId].getIsReady()){
            System.out.println("Nota dengan ID "+notaList[indexId].getIdNota()+" berhasil diambil!");
            for(int i = 0; i < notaList.length; i++){
                if(notaList[i].getIdNota() != idAmbil){
                    tempNota[indexTemp] = notaList[i];
                    indexTemp++;
                }
            }
            notaList = tempNota;
        }else if(cekId && !notaList[indexId].getIsReady()){
            System.out.println("Nota dengan ID "+notaList[indexId].getIdNota()+" gagal diambil!");
        }else{
            System.out.println("Nota dengan ID "+ idAmbil+" tidak ditemukan!");
        }
    }

    private static void handleNextDay() {
        cal.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println("Dek Depe tidur hari ini... zzz...");
        for(int i = 0; i < notaList.length; i++){
            notaList[i].kurangHari();
            notaList[i].bisaAmbil();
            if(notaList[i].getIsReady()){
                System.out.println("Laundry dengan nota ID "+notaList[i].getIdNota()+" sudah dapat diambil!");
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima, int discountCounter){
        //Membuat format tanggal terima dan tanggal selesai menjadi "dd/mm/yyyy"
        DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkFormat = LocalDate.parse(tanggalTerima, formatDay);
        
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
        //Membuat String perbaris
        String idShow = "ID    : "+id+"\n";
        String paketShow = "Paket : "+paket+"\n";
        String hargaShow = "Harga :\n"+ berat+" kg x "+hargaPaket+" = "+totalHarga +"\n";
        String terimaDate = "Tanggal Terima  : "+tanggalTerima+"\n";
        String selesaiDate = "Tanggal Selesai : "+ strSelesai;
        String status = "\nStatus      	: Belum bisa diambil :(";

        if(discountCounter == 3){
            int hargaDisc = totalHarga/2;
            hargaShow = "Harga :\n"+ berat+" kg x "+hargaPaket+" = "+totalHarga +" = "+hargaDisc+" (Discount member 50%!!!)\n";
        }
        //Menyatukan semua String yang dibuat untuk direturn
        String finalReturn = idShow+paketShow+hargaShow+terimaDate+selesaiDate+status;
        return finalReturn;
    }
}