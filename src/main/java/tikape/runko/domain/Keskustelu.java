package tikape.runko.domain;

public class Keskustelu {
    private int id;
    private int avausID;
    private String viesti;
    private String time;
    public Keskustelu(int id, String viesti, String time, int avausID){
        this.id=id;
        this.avausID=avausID;
        this.viesti=viesti;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public int getAvausID() {
        return avausID;
    }

    public String getViesti() {
        return viesti;
    }

    public String getTime() {
        return time;
    }
    
    @Override
    public String toString(){
        return "("+this.id+" --- "+this.time+")\n"+this.viesti;
    }
}
