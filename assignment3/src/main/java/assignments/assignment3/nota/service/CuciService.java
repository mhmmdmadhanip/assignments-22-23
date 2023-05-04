package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    protected boolean statusCuci = false;
    @Override
    public String doWork() {
        statusCuci = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return statusCuci;
    }

    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
