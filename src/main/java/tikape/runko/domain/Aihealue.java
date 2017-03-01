package tikape.runko.domain;

public class Aihealue {

    private Integer id;
    private String nimi;
    private Integer lkm;
    private String time;

    public Aihealue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Aihealue(Integer id, String nimi, Integer lkm, String time) {
        this.id = id;
        this.nimi = nimi;
        this.lkm = lkm;
        this.time = time;
    }

    public Integer getLkm() {
        return lkm;
    }

    public String getTime() {
        return time;
    }

    public void setLkm(Integer lkm) {
        this.lkm = lkm;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return this.nimi + " (" + this.id + ")";
    }
}
