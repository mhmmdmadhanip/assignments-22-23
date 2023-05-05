package assignments.assignment3.nota;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
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
    private int hariKeterlambatan;
    private boolean terlambat = true;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        tanggalMasuk = tanggal;
    }


    public void addService(LaundryService service){
        //memasukkan service ke array services
        services = Arrays.copyOf(services, services.length + 1);
        services[services.length-1] = service;
    }

    public String kerjakan(){
        boolean cekStatus = true;
        for (int i = 0; i < services.length; i++) {
            LaundryService service = services[i];
            //print doWork setiap service juga belum selesai
            String printNota = "Nota "+ id +" : ";
            if(service instanceof CuciService){
                if(!service.isDone()){
                    System.out.println(printNota + service.doWork());
                    cekStatus = false;
                    break;
                }else{
                    continue;
                }
            }else if (service instanceof SetrikaService){
                if(!service.isDone()){
                    System.out.println(printNota + service.doWork());
                    cekStatus = false;
                    break;
                }else{
                    continue;
                }
            }else if (service instanceof AntarService){
                if(!service.isDone()){
                    System.out.println(printNota + service.doWork());
                    cekStatus = false;
                    break;
                }else{
                    continue;
                }
            }
        }
        //jika semua service sudah selesai maka akan mereturn status laundry
        if(cekStatus){
            return getNotaStatus() + "\n";
        }
        return "";
    }
    public void toNextDay() {
        //mengurangi sisa hari jika masih lebih dari 0
        if(sisaHariPengerjaan > 0){
            sisaHariPengerjaan--;
        }else{ 
            //sisa hari sudah 0 namun service belum selesai
            for (int i = 0; i < services.length; i++) {
                if(!services[i].isDone()){
                    terlambat = false;
                    hariKeterlambatan++;
                    break;
                }
            }
        }
    }

    public long calculateHarga(){
        //menentukan base harga sesuai paket
        if(paket.equalsIgnoreCase("reguler")){
            baseHarga = 7000;
        }else if(paket.equalsIgnoreCase("fast")){
            baseHarga = 10000;
        }else{
            baseHarga = 12000;
        }
        //menghitung total harga sebelum service
        tempHarga = berat*baseHarga;
        totalHarga = tempHarga;
        //menghitung total harga dengan service
        for (int i = 0; i < services.length; i++) {
            if(services[i] instanceof SetrikaService || services[i] instanceof AntarService){
                totalHarga += services[i].getHarga(berat);
            }
        }
        //menghitung total keterlambatan dan jika harga kurang dari 0 akan dijadikan 0
        if(hariKeterlambatan > 0 && !terlambat){
            totalHarga -= (hariKeterlambatan*2000);
            if(totalHarga < 0){
                totalHarga = 0;
            }
        }
        return totalHarga;
    }

    //set id nota
    public void setIdNota(int idNota){
        this.id = idNota;
    }

    //set sisa hari
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
        for (int i = 0; i < services.length; i++) {
            //jika ada service yang belum maka status menjadi belum selesai
            if(!services[i].isDone()){
                return "Nota " + id + " : Belum selesai.";
            }
        }
        //semua service sudah selesai
        return "Nota " + id + " : Sudah selesai.";
    }

    @Override
    public String toString(){
        calculateHarga(); //menghitung total harga
        //buat pola tanggal dan parse tanggal masuk
        DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkFormat = LocalDate.parse(tanggalMasuk, formatDay);
        
        String strSelesai = "";
        //menambah hari dan merubah menjadi str
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
        //mencetak service
        String strServices = "\n--- SERVICE LIST ---\n";
        String serviceStr = "";
        for (int i = 0; i < services.length; i++) {
            serviceStr = "-" + services[i].getServiceName() + " @ Rp." + services[i].getHarga(berat) + "\n";
            strServices += serviceStr;
        }
        String hargaAkhir = "Harga Akhir: " + totalHarga;
        //harga akhir jika terlambat
        if(hariKeterlambatan > 0){
            hargaAkhir = hargaAkhir + " Ada kompensasi keterlambatan " + hariKeterlambatan + " * 2000 hari"; 
        }
        return "[ID Nota = "+ id + "]\nID    : " + member.getId()+
        "\nPaket : " + paket + "\nHarga :\n"+
        berat + " kg x " + baseHarga + " = "+
        tempHarga + "\ntanggal terima  : " + tanggalMasuk +
        "\ntanggal selesai : " + strSelesai +
        strServices + hargaAkhir;
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

    public int getId(){
        return id;
    }
}
