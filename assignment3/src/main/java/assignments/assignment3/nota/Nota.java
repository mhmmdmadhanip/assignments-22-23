package assignments.assignment3.nota;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services = new LaundryService[0];
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;
    private long tempHarga;
    private long totalHarga;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        tanggalMasuk = tanggal;
    }

    public void addService(LaundryService service){
        services = Arrays.copyOf(services, services.length + 1);
        services[services.length-1] = service;
    }

    public String kerjakan(){
        // TODO
        return "";
    }
    public void toNextDay() {
        // TODO
    }

    public long calculateHarga(){
        if(paket.equalsIgnoreCase("reguler")){
            baseHarga = 7000;
        }else if(paket.equalsIgnoreCase("fast")){
            baseHarga = 10000;
        }else{
            baseHarga = 12000;
        }
        tempHarga = berat*baseHarga;
        totalHarga = tempHarga;
        for (int i = 0; i < services.length; i++) {
            if(services[i] instanceof SetrikaService || services[i] instanceof AntarService){
                totalHarga += services[i].getHarga(berat);
            }
        }
        return totalHarga;
    }

    public void setIdNota(int idNota){
        this.id = idNota;
    }

    public void setSisaHariPengerjaan(String paket){
        if(paket.equalsIgnoreCase("reguler")){
            sisaHariPengerjaan = 3;
        }else if(paket.equalsIgnoreCase("fast")){
            sisaHariPengerjaan = 2;
        }else{
            sisaHariPengerjaan = 1;
        }
    }

    public String getNotaStatus(){
        return "";
    }

    @Override
    public String toString(){
        calculateHarga();
        DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkFormat = LocalDate.parse(tanggalMasuk, formatDay);
        
        String strSelesai = "";
        if(paket.equalsIgnoreCase("express")){
            LocalDate tanggalSelesai = checkFormat.plusDays(1);
            strSelesai = tanggalSelesai.format(formatDay);
        }else if(paket.equalsIgnoreCase("fast")){
            LocalDate tanggalSelesai = checkFormat.plusDays(2);
            strSelesai = tanggalSelesai.format(formatDay);
        }else if(paket.equalsIgnoreCase("reguler")){
            LocalDate tanggalSelesai = checkFormat.plusDays(3);
            strSelesai = tanggalSelesai.format(formatDay);
        }
        String strServices = "";
        for (int i = 0; i < services.length; i++) {
            strServices = strServices + "-" + services[i].getServiceName() + " @ Rp." + services[i].getHarga(berat) + "\n";
        }
        return "[ID Nota = "+ id + "]\nID    : " + member.getId()+
        "\nPaket : " + paket + "\nHarga :\n"+
        berat + " kg x " + baseHarga + " = "+
        tempHarga + "\ntanggal terima  : " + tanggalMasuk +
        "\ntanggal selesai : " + strSelesai + "\n--- SERVICE LIST ---\n" +
        strServices + "Harga Akhir: " + totalHarga;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }
}
