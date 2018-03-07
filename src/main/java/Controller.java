import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static spark.Spark.*;


public class Controller {
    private static ShopService service = new ShopService();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        staticFileLocation("/");
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");

            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        get("/getAll", (request, response) -> {
            String json = mapper.writeValueAsString(service.getAll());
            return json;
        });
//        post("/addGoods", (request, response) -> {
//            try {
//                String body = request.body();
//                List<Good> goods = mapper.readValue(body, new TypeReference<List<Good>>() {
//                });
//                service.addGoods(goods);
//                return "ok";
//            } catch (JsonParseException | JsonMappingException e) {
//                e.printStackTrace();
//            }
//
//            return "error";
//        });
        post("/addGoods",(request, response) -> {
            try
            {
                String body = request.body();
                service.addGoods(body);
                return "ok";
            } catch (JsonParseException | JsonMappingException e) {
                e.printStackTrace();
            }
            return "error";
        });
        post("/buyGoods", (request, response) -> {

            try {
                String body = request.body();
                List<Good> goods = mapper.readValue(body, new TypeReference<List<Good>>() {
                });
                service.buyGoods(goods);
                return "ok";
            } catch (JsonParseException | JsonMappingException e) {
                e.printStackTrace();
            }

            return "error";
        });

    }
}