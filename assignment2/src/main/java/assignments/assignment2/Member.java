package assignments.assignment2;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    public Member(String nama, String noHp, String id, int bonusCounter) {
        this.nama = nama;
        this.noHp = noHp; 
        this.id = id;
        this.bonusCounter = bonusCounter;
    }
    //membuat getter dan setter
    public String getNama(){
        return this.nama;
    }
    public String getNomor(){
        return this.noHp;
    }
    public String getId(){
        return this.id;
    }
    public int getBonusCounter(){
        return this.bonusCounter;
    }
    //menambahkan bonus dan jika bonus sudah 3 akan direset kembali
    public void tambahBonus(){
        if(this.bonusCounter == 3){
            this.bonusCounter = 0;
            this.bonusCounter++;
        }else{
            this.bonusCounter++;
        }
    }
}