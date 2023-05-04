package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    protected boolean statusSetrika = false;

    @Override
    public String doWork() {
        statusSetrika = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return statusSetrika;
    }

    @Override
    public long getHarga(int berat) {
        long hargaSetrika = berat * 1000;
        return hargaSetrika;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
