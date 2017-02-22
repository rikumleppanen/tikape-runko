package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AihealueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.KeskustelunavausDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.Keskustelu;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:kanta.db");
        database.init();
        
        AihealueDao aihealueDao = new AihealueDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        List<String> list = new ArrayList<>();
        
        for (int i = 1; i <= aihealueDao.size(); i++) {
            list.add(aihealueDao.findOne(i).toString());
        }
        
        get("/", (req, res) -> {
            HashMap<String, Object> data = new HashMap<>();
            
            if (req.queryParams().contains("content")) {
                list.add(req.queryParams("content"));
                aihealueDao.add(req.queryParams("content"));
            }
            
            data.put("list", list);
            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());
        
        System.out.println(aihealueDao.findOne(1));
        System.out.println("");

        //testejä (en pystynyt vielä testaamaan omalla läppärillä, kokeilen kampuksella sitten)
        KeskustelunavausDao keskustelunavausDao = new KeskustelunavausDao(database);
        System.out.println(keskustelunavausDao.findOne(2));
        
        System.out.println("");
        System.out.println(keskusteluDao.findOne(1));
        for (Keskustelu k : keskusteluDao.findAll()) {
            System.out.println(k);
        }

//        Database database = new Database("jdbc:sqlite:opiskelijat.db");
//        database.init();
//
//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
//
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
