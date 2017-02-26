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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE viestiID=?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("viestiID");
        Integer avausID = rs.getInt("avaus");
        String viesti = rs.getString("teksti");
        String time = rs.getString("timestamp");
        String nimimerkki = rs.getString("nimimerkki");

        Keskustelu o = new Keskustelu(id, viesti, nimimerkki, time, avausID);

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
            Integer id = rs.getInt("viestiID");
            Integer avausID = rs.getInt("avaus");
            String viesti = rs.getString("teksti");
            String time = rs.getString("timestamp");
            String nimimerkki = rs.getString("nimimerkki");
            
            avaukset.add(new Keskustelu(id, viesti, nimimerkki, time, avausID));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }
    
    public List<Keskustelu> findAllWithKey(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE avaus=?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> avaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("viestiID");
            Integer avausID = rs.getInt("avaus");
            String viesti = rs.getString("teksti");
            String time = rs.getString("timestamp");
            String nimimerkki = rs.getString("nimimerkki");
            
            avaukset.add(new Keskustelu(id, viesti, nimimerkki, time, avausID));
        }

        rs.close();
        stmt.close();
        connection.close();

        return avaukset;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Keskustelu WHERE viestiID = "+key+";");

        ResultSet rs = stmt.executeQuery();
        
        rs.close();
        stmt.close();
        connection.close();
    }
    
    public void add(String viesti, String nimimerkki, String alueID) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelu (teksti, nimimerkki, avaus) "
                + "VALUES (?, ?, ?)");
        stmt.setObject(1, viesti);
        stmt.setObject(2, nimimerkki);
        stmt.setObject(3, alueID);
        stmt.execute();
        
        stmt.close();
        conn.close();
    }

}

