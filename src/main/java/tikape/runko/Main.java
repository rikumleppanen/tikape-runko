package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AihealueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.KeskustelunavausDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Keskustelunavaus;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:kanta1.db";
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }
        Database database = new Database(jdbcOsoite);
        database.init();

        AihealueDao aihealueDao = new AihealueDao(database);
        KeskustelunavausDao keskustelunavausDao = new KeskustelunavausDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        List<String> list = new ArrayList<>();

        Spark.get("/", (req, res) -> {
            res.redirect("/aihealueet");
            return "ok";
        });

        Spark.get("/aihealueet", (req, res) -> {
            HashMap data = new HashMap<>();
            data.put("aihealueet", aihealueDao.haeKaikki());

            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/aihealueet", (req, res) -> {
            aihealueDao.lisaa(req.queryParams("aihealue"));
            res.redirect("/");
            return "ok";
        });

        //Listaa keskustelunavaukset aiheittain
        Spark.get("/aihealueet/:id", (req, res) -> {

            HashMap<String, Object> data = new HashMap<>();
            data.put("aihe", aihealueDao.findOne(Integer.parseInt(req.params(":id"))).getNimi());

            data.put("avaukset", keskustelunavausDao.haeKaikki(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(data, "keskustelunavaukset");
        }, new ThymeleafTemplateEngine());

        Spark.post("/aihealueet/:id", (req, res) -> {
            keskustelunavausDao.add(req.queryParams("kuvaus"), req.params(":id"));
            res.redirect("/aihealueet/" + req.params(":id"));
            return "ok";
        });

        Spark.get("/keskustelu/:id", (req, res) -> {

            HashMap<String, Object> data = new HashMap<>();
            data.put("viestit", keskusteluDao.findAllWithKey(Integer.parseInt(req.params(":id"))));

            data.put("alue", aihealueDao.findOne(keskustelunavausDao.findOne(Integer.parseInt(req.params(":id"))).getAiheID()));

            data.put("keskustelunavaus", keskustelunavausDao.findOne(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(data, "keskustelu");
        }, new ThymeleafTemplateEngine());

        Spark.post("/keskustelu/:id", (req, res) -> {
            keskusteluDao.add(req.queryParams("viesti"), req.queryParams("nimimerkki"), req.params(":id"));
            res.redirect("/keskustelu/" + req.params(":id"));
            return "ok";
        });

//        System.out.println(aihealueDao.findOne(1));
//        System.out.println("");
//        System.out.println(keskusteluDao.findOne(1));
//        for (Keskustelu k : keskusteluDao.findAll()) {
//            System.out.println(k);
//        }
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
