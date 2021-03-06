package nl.han.gateway.controller;

import com.google.gson.Gson;
import nl.han.gateway.exceptions.NotFoundException;
import nl.han.gateway.models.Poot;
import nl.han.gateway.service.PotenService;
import spark.Request;
import spark.Response;

import java.util.List;

import static nl.han.gateway.util.transformers.JsonUtil.json;
import static spark.Spark.*;

public class PotenController {
    private Gson gson = new Gson();
    private PotenService potenService;

    public PotenController() {
        this.potenService = new PotenService();

        put("/poten/:pootid", (this::savePootConfiguration), json());
        get("/poten", (this::getAllPoten), json());
        get("/poten/:pootid", (this::getPoot), json());
        delete("/poten/:pootid", (this::deletePoot));
    }

    /**
     * Removes a Poot and resets the EEPROM of the poot
     *
     * @param request
     * @param response
     * @return void
     */
    private String deletePoot(Request request, Response response) {
        Poot poot;
        try {
            poot = this.potenService.getPoot(Long.valueOf(request.params("pootid")));
            potenService.resetPoot(poot);
            response.status(200);
        } catch (NotFoundException e) {
            response.status(404);
            return e.getMessage();
        }

        return "Ok";
    }

    /**
     * Get info of one poot
     *
     * @param request
     * @param response
     * @return
     */
    private Poot getPoot(Request request, Response response) {
        Poot poot = null;
        try {
            poot = this.potenService.getPoot(Long.valueOf(request.params("pootid")));
        } catch (NotFoundException e) {
            response.status(404);
        }
        return poot;
    }


    /**
     * Saves the poot to the PotenService
     *
     * @param request
     * @param response
     */
    private String savePootConfiguration(Request request, Response response) {
        Poot incommingConfig = gson.fromJson(request.body(), Poot.class);
        Poot poot = null;

        try {
            poot = potenService.getPoot(Long.valueOf(request.params("pootid")));
            poot.setWeetjes(incommingConfig.getWeetjes());
            poot.setDierengeluid(incommingConfig.getDierengeluid());
            this.potenService.savePootConfig(poot);
            response.type("application/json");
        } catch (NotFoundException e) {
            response.status(404);
            return e.getMessage();
        }
        response.status(200);
        return "";

    }

    /**
     * Get a list of all known poten
     * todo: add to Swagger!
     *
     * @param request
     * @param response
     * @return
     */
    private List<Poot> getAllPoten(Request request, Response response) {
        response.type("application/json");
        return this.potenService.getAllPoten();
    }


}
