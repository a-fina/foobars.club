/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package club.foobars;

import static spark.Spark.*;
import static j2html.TagCreator.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
;

/**
 *
 * @author alessio.finamore
 */
public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());

        Random r = new Random();
        int i = r.nextInt(6);
        String[] colors = {"yellow","red","blue", "white","orange", "purple", "green"};
        
        Map map = new HashMap();
        map.put("background-color", colors[i]);

        get("/", (rq, rs) -> 
                new ModelAndView(map, "cover.html"), 
                new MustacheTemplateEngine()
        );
        
         get("/foobar.club", (req, res) -> {
            return html().with(
                    head().with(
                            title("Foo, Bars & Wheels - Club"),
                            link().withRel("stylesheet").withHref("css/main.css")
                    ),
                    body().with(
                        img().withSrc("img/costina-mtb-small.png")
                    )
            ).toString();
        });
        
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
