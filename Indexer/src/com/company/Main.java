package com.company;
import static spark.Spark.*;
import com.google.gson.Gson;

public class Main {

        public static void main(String[] args) {

            Gson gson = new Gson();
            get("/index", (req, res) ->
                    new Result(req.queryParams("query")),gson::toJson
            );

            //get("/index", (req,res) -> )
        }
}
