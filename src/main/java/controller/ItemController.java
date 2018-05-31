package controller;


import com.google.gson.Gson;
import controller.util.StandardResponse;
import controller.util.StatusResponse;
import domain.Item;
import exception.ItemException;
import service.ItemService;
import service.ItemServiceImpl;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ItemController {


    public static void main(String[] args) {

        final ItemService itemService = new ItemServiceImpl();
        port(8080);

        get("/items/:id", (request, response) -> {
            response.type("application/json");
            try{
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                        .toJsonTree(itemService.getItem(request.params("id")))));
            }catch (ItemException e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });
        get("/items", (request, response) -> {
            response.type("application/json");
            try{
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                        .toJsonTree(itemService.getItems())));
            }catch (ItemException e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });
        post("/items", (request, response) -> {
            response.type("application/json");
            try{
                Item item = new Gson().fromJson(request.body(), Item.class);
                itemService.addItem(item);
                return new Gson().
                        toJson(new StandardResponse(StatusResponse.SUCCESS,"Item guardado"));
            }catch (ItemException e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }catch (Exception e){
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El formato de los datos enviados no es correcto"));

            }

        });
        put("/items/:id", (request, response) -> {
            response.type("application/json");
            try{
                Item toEdit = new Gson().fromJson(request.body(), Item.class);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
                        .toJsonTree(itemService.updateItem(toEdit))));
            }catch (ItemException e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });
        delete("items/:id", (request, response) -> {
            response.type("application/json");
            try{
                itemService.deleteItem(request.params("id"));
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Item borrado"));
            }catch (ItemException e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
            }
        });

        /** ----------------- VIEWS ------------------------ */
        get("/nuevo-item", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/nuevo-item.vm")
            );
        });
        get("/listado-item", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("items", itemService.getItems());
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/listado-item.vm")
            );
        });
        get("/modificar-item/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("item", itemService.getItem(req.params("id")));
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/modificar-item.vm")
            );
        });
    }

}
