package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;

public class AihealueDao implements Dao<Aihealue, Integer> {

    private Database database;

    public AihealueDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihealue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue WHERE aiheID = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("aiheid");
        String nimi = rs.getString("nimi");

        Aihealue o = new Aihealue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Aihealue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aihealueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("aiheid");
            String nimi = rs.getString("nimi");

            aihealueet.add(new Aihealue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;
    }

    public List<Aihealue> haeKaikki() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT a.aiheid, a.nimi, taulu.lkm, taulu.aikaleima\n"
                + "FROM Aihealue a LEFT JOIN (\n"
                + "    SELECT kas.aihe as ID, COUNT(ku.viestiID) as lkm, MAX(ku.timestamp) as aikaleima\n"
                + "    FROM Keskustelunavaus kas, Keskustelu ku\n"
                + "    WHERE kas.avausID = ku.avaus\n"
                + "    GROUP BY kas.aihe) taulu\n"
                + "    ON a.aiheID = taulu.ID\n"
                + "ORDER BY a.nimi ASC;");

        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aihealueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("aiheid");
            String nimi = rs.getString("nimi");
            Integer lkm = rs.getInt("lkm");
            String time = rs.getString("aikaleima");

            if (lkm == null) {
                aihealueet.add(new Aihealue(id, nimi, null, null));
            }
            aihealueet.add(new Aihealue(id, nimi, lkm, time));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;
    }

    public int size() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(aiheID) as lkm FROM Aihealue");
        ResultSet rs = stmt.executeQuery();
        Integer luku = rs.getInt("lkm");
        rs.close();
        stmt.close();
        connection.close();

        return luku;
    }

    public void lisaa(String aihe) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Aihealue (nimi) "
                + "VALUES (?)");
        stmt.setString(1, aihe);
        stmt.execute();

        connection.close();

    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
