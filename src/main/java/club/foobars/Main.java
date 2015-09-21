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
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;import spark.template.mustache.MustacheTemplateEngine;
;

/**
 *
 * @author alessio.finamore
 */
public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());


        Map map = new HashMap();
        map.put("name", "Sam");
        // hello.mustache file is in resources/templates directory
        get("/mustache", (rq, rs) -> 
                new ModelAndView(map, "hello.mustache"), 
                new MustacheTemplateEngine()
        );
        

        get("/hello", (req, res) -> "Hello Heroku World");

        get("/foobar.club", (req, res) -> {
            return html().with(
                    head().with(
                            title("Foo, Bars & Wheels - Club"),
                            link().withRel("stylesheet").withHref("css/main.css")
                    ),
                    body().with(
                        img().withSrc("img/costina-mtb-big.png")
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
