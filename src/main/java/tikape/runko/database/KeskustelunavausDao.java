package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelunavaus;

public class KeskustelunavausDao implements Dao <Keskustelunavaus, Integer>{

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
        //timestamp?
        Integer id = rs.getInt("avausID");
        String kuvaus = rs.getString("kuvaus");
        Integer aihe = rs.getInt("aihe");

        Keskustelunavaus o = new Keskustelunavaus(id, kuvaus, aihe);

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
            //timestamp?
            Integer id = rs.getInt("id");
            String kuvaus = rs.getString("kuvaus");
            Integer aihe = rs.getInt("aihe");

            avaukset.add(new Keskustelunavaus(id, kuvaus, aihe));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}

