package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
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
        //menentukan menu sesuai pilihan
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
        System.out.println("Stand back! " + super.loginMember.getNama() + " beginning to nyuci!");
        //print semua nota dan service yang sesuai
        for (int i = 0; i < NotaManager.notaList.length; i++) {
            System.out.print(NotaManager.notaList[i].kerjakan());
        }
        System.out.println();
    }

    private void displayNota(){
        //menampilkan semua nota yang ada dan statusnya
        for (int i = 0; i < NotaManager.notaList.length; i++) {
            System.out.println(NotaManager.notaList[i].getNotaStatus());
        }
        System.out.println();
    }
    
}