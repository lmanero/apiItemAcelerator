package controller;


import com.google.gson.Gson;
import controller.util.StandardResponse;
import controller.util.StatusResponse;
import domain.Item;
import service.ItemService;
import service.ItemServiceImpl;

import static spark.Spark.*;

public class ItemController {


    public static void main(String[] args) {

        //ver accesibilidad
        ItemService itemService = new ItemServiceImpl();

        port(8080);
        post("/items/:id", (request, response) -> {
            response.type("application/json");
            Item item = new Gson().fromJson(request.body(), Item.class);
            itemService.addItem(item);
            return new Gson().
                    toJson(new StandardResponse(StatusResponse.SUCCESS));
        });
        get("/items/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                    .toJsonTree(itemService.getItem(request.params("id")))));
        });
        get("/items", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                    .toJsonTree(itemService.getItems())));
        });
        put("/items/:id", (request, response) -> {
            response.type("application/json");
            //validar si existe
            Item toEdit = new Gson().fromJson(request.body(), Item.class);
            Item editItem = itemService.updateItem(toEdit);
            if(editItem != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                        .toJsonTree(editItem)));
            } else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Item no encontrado"));
            }
        });
        delete( "items/:id", (request, response) -> {
            response.type("application/json");
            itemService.deleteItem(request.params("id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Item borrado"));
        });
    }
}
