package tikape.runko.domain;

public class Keskustelu {
    private int id;
    private int avausID;
    private String viesti;
    private String time;
    private String nimimerkki;
    public Keskustelu(int id, String viesti, String nimimerkki, String time, int avausID){
        this.id=id;
        this.avausID=avausID;
        this.viesti=viesti;
        this.time=time;
        this.nimimerkki=nimimerkki;
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
    
    public String getNimimerkki(){
        return this.nimimerkki;
    }
    
    @Override
    public String toString(){
        return this.nimimerkki+" - "+this.time+": "+this.viesti;
    }
}
