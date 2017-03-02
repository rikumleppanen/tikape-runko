package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelunavaus;

public class KeskustelunavausDao implements Dao<Keskustelunavaus, Integer> {

    private Database database;

    public KeskustelunavausDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelunavaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE avausID = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("avausID");
        String kuvaus = rs.getString("kuvaus");
        int aiheID = rs.getInt("aihe");
        String time = rs.getString("timestamp");

        Keskustelunavaus o = new Keskustelunavaus(id, kuvaus, time, aiheID);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Keskustelunavaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> avaukset = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("avausID");
            String kuvaus = rs.getString("kuvaus");
            int aiheID = rs.getInt("aihe");
            String time = rs.getString("timestamp");

            avaukset.add(new Keskustelunavaus(id, kuvaus, time, aiheID));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    public List<Keskustelunavaus> findAll(int key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE aihe = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Keskustelunavaus> avaukset = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("avausID");
            String kuvaus = rs.getString("kuvaus");
            int aiheID = rs.getInt("aihe");
            String time = rs.getString("timestamp");

            if (aiheID == key) {
                avaukset.add(new Keskustelunavaus(id, kuvaus, time, aiheID));
            }
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    public List<Keskustelunavaus> haeKaikki(int aihe) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT *\n"
                + "FROM Keskustelunavaus ka LEFT JOIN (\n"
                + "    SELECT ku.avaus as ID, COUNT(ku.viestiID) as lkm, MAX(ku.timestamp) as aikaleima\n"
                + "    FROM Keskustelu ku\n"
                + "    GROUP BY ku.avaus) taulu\n"
                + "    ON ka.avausID = taulu.ID\n"
                + "    WHERE ka.aihe = ?\n"
                + "ORDER BY ka.kuvaus ASC;");

        stmt.setObject(1, aihe);
        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelunavaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("avausID");
            String nimi = rs.getString("kuvaus");
            Integer lkm = rs.getInt("lkm");
            String time = rs.getString("aikaleima");
            int aiheID = rs.getInt("aihe");
            String time2 = rs.getString("timestamp");

            if (lkm == null) {
                keskustelunavaukset.add(new Keskustelunavaus(id, nimi, time2, aiheID));
            } else {
                keskustelunavaukset.add(new Keskustelunavaus(id, nimi, time, lkm, aiheID));
            }

        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelunavaukset;
    }

    public int size(int key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(avausID) as lkm FROM Keskustelunavaus WHERE aihe = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt("lkm");
        rs.close();
        stmt.close();
        connection.close();

        return luku;
    }

    public void add(String kuvaus, String aiheID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelunavaus (kuvaus, aihe) "
                + "VALUES ('" + kuvaus + "', " + aiheID + ")");
        stmt.execute();

        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Keskustelunavaus WHERE avausID = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();

        rs.close();
        stmt.close();
        connection.close();
    }

}
