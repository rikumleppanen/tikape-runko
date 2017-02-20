package tikape.runko.domain;

public class Keskustelunavaus {

    private Integer id;
    private String kuvaus;
    private Integer aihe;

    //timestamp?
    public Keskustelunavaus(Integer id, String kuvaus, Integer aihe) {
        this.id = id;
        this.kuvaus = kuvaus;
        this.aihe = aihe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    
    public Integer getAihe(){
        return aihe;
    }
    
    public void setAihe(Integer aihe){
        this.aihe = aihe;
    }

    @Override
    public String toString() {
        return "(" + this.id + ") " + this.kuvaus + " - Aihealue: " + this.aihe;
    }
}
