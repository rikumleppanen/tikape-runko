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

    //onko tarpeen?
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

    public void add(String kuvaus, String aiheID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelunavaus (kuvaus, aiheID) "
                + "VALUES ('" + kuvaus + "', " + aiheID + ")");
        stmt.execute();

        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE * FROM Keskustelunavaus WHERE avausID = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();

        rs.close();
        stmt.close();
        connection.close();
    }

}