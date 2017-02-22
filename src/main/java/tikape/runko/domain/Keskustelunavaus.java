package tikape.runko.domain;

public class Keskustelunavaus {

    private int id;
    private String kuvaus;
    private int aiheID;
    private String time;

    //timestamp?
    public Keskustelunavaus(int id, String kuvaus, String time, int aiheID) {
        this.id = id;
        this.kuvaus = kuvaus;
        this.aiheID = aiheID;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAiheID() {
        return aiheID;
    }

    public void setAiheID(Integer aiheID) {
        this.aiheID = aiheID;
    }

    @Override
    public String toString() {
        return "(" + this.id + ") " + this.kuvaus + " (" + this.time + ") - Aihealue: " + this.aiheID;
    }
}
