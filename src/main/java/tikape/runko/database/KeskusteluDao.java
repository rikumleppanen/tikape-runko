package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;

public class KeskusteluDao implements Dao <Keskustelu, Integer>{

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE viestiID = "+key+";");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        //timestamp?
        Integer id = rs.getInt("viestiID");
        Integer avausID = rs.getInt("avaus");
        String viesti = rs.getString("teksti");
        String time = rs.getString("timestamp");

        Keskustelu o = new Keskustelu(id, viesti, time, avausID);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> avaukset = new ArrayList<>();
        while (rs.next()) {
            //timestamp?
            Integer id = rs.getInt("viestiID");
            Integer avausID = rs.getInt("avaus");
            String viesti = rs.getString("teksti");
            String time = rs.getString("timestamp");

            avaukset.add(new Keskustelu(id, viesti, time, avausID));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE * FROM Keskustelu WHERE viestiID = "+key+";");

        ResultSet rs = stmt.executeQuery();
        
        rs.close();
        stmt.close();
        connection.close();
    }

}

