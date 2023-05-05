package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay(){
        //menambah hari 
        cal.add(Calendar.DAY_OF_YEAR, 1);
        //update semua nota
        for (int i = 0; i < notaList.length; i++) {
            notaList[i].toNextDay();
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota){
        //memasukkan nota ke dalam array nota
        notaList = Arrays.copyOf(notaList, notaList.length+1);
        notaList[notaList.length-1] = nota;
    }

}
