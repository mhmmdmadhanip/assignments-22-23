package assignments.assignment2;

public class Nota {
    private int idNota;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private Member member;

    public Nota(Member member, String paket, int berat, String tanggalMasuk, int idNota, int sisaHariPengerjaan, boolean isReady) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNota = idNota;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
        this.isReady = isReady;
    }

    public Member getMember(){
        return this.member;
    }
    public String getPaket(){
        return this.paket;
    }
    public int getBerat(){
        return this.berat;
    }
    public int getIdNota(){
        return this.idNota;
    }
    public int getSisaHari(){
        return this.sisaHariPengerjaan;
    }
    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }
    public boolean getIsReady(){
        return this.isReady;
    }
    public void kurangHari(){
        if(this.sisaHariPengerjaan != 0){
            this.sisaHariPengerjaan--;
        }
    }
    public void bisaAmbil(){
        if(this.sisaHariPengerjaan == 0){
            this.isReady = true;
        }
    }
}
