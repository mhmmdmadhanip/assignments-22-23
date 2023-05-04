package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        // TODO:
        if(choice == 1){
            doLaundry();
        }else if(choice == 2){
            displayNota();
        }else if(choice == 3){
            logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    private void doLaundry(){
        int idNota = 0;
        System.out.println("Stand back! " + super.loginMember.getNama() + " beginning to nyuci!");
        for (int i = 0; i < NotaManager.notaList.length; i++) {
            String printNota = "Nota "+ idNota +" : ";
            for (int j = 0; j < NotaManager.notaList[i].getServices().length; j++) {
                LaundryService service = NotaManager.notaList[i].getServices()[j];
                if(service instanceof CuciService){
                    if(!service.isDone()){
                        System.out.println(printNota + service.doWork());
                        break;
                    }else{
                        continue;
                    }
                }else if (service instanceof SetrikaService){
                    if(!service.isDone()){
                        System.out.println(printNota + service.doWork());
                        break;
                    }else{
                        continue;
                    }
                }else if (service instanceof AntarService){
                    if(!service.isDone()){
                        System.out.println(printNota + service.doWork());
                        break;
                    }else{
                        System.out.println(printNota + " Sudah selesai.");
                    }
                }

            }
            
            idNota++;
        }
        System.out.println();
    }

    private void displayNota(){
        int idNota = 0;
        for (int i = 0; i < NotaManager.notaList.length; i++) {
            String printNota = "Nota "+ idNota +" : ";
            for (int j = 0; j < NotaManager.notaList[i].getServices().length; j++) {
                LaundryService service = NotaManager.notaList[i].getServices()[j];
                if(service instanceof CuciService){
                    if(!service.isDone()){
                        System.out.println(printNota + "Belum selesai.");
                        break;
                    }else{
                        continue;
                    }
                }else if (service instanceof SetrikaService){
                    if(!service.isDone()){
                        System.out.println(printNota + "Belum selesai.");
                        break;
                    }else{
                        continue;
                    }
                }else if (service instanceof AntarService){
                    if(!service.isDone()){
                        System.out.println(printNota + "Belum selesai.");
                        break;
                    }else{
                        System.out.println(printNota + " Sudah selesai.");
                    }
                }

            }
            idNota++;
        }
    }
}