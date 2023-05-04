package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    protected boolean statusAntar = false;
    @Override
    public String doWork() {
        statusAntar = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return statusAntar;
    }

    @Override
    public long getHarga(int berat) {
        long hargaAntar = berat*500;
        if(hargaAntar < 2000){
            hargaAntar = 2000;
        }
        return hargaAntar;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
