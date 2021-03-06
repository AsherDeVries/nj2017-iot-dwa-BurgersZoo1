package nl.han.backend.services.group1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import nl.han.backend.services.BackendPootServiceBase;
import nl.han.gateway.dao.GsonParserUtil;
import nl.han.gateway.exceptions.NotFoundException;
import nl.han.gateway.models.Poot;
import nl.han.gateway.util.GatewayProperties;
import nl.han.mysensor.models.MySetMessage;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.bson.types.ObjectId;

import java.io.IOException;


/**
 * Implementation for communicating with group 1 backend
 */
public class BackendPootService extends BackendPootServiceBase {
    public BackendPootService() {
        super("http://" + GatewayProperties.getProperty("backend.group1.ip") +
                ":" + GatewayProperties.getProperty("backend.group1.port")
                + GatewayProperties.getProperty("backend.group1.baseApiUrl"));
    }

    @Override
    public void sendRangerCardScanToBackend(MySetMessage message, Poot poot) {
        Long cardId = super.parseCardIdToDec(message.getPayload());
        RequestBody body = RequestBody.create(JSON, String.valueOf(cardId));
        Request request = new Request.Builder()
                .url(this.baseUri + "/poten/" + poot.getPootid() + "/scan")
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            if (response.code() == 201) {
                logger.debug("Poot scan was successfully registered at the backend");
            } else if (response.code() == 400) {
                logger.error("Ranger doesn't exist");
            } else if (response.code() == 404) {
                logger.error("Could not find poot");
            } else if (response.code() == 500) {
                logger.error("Ranger scan is not saved!");
            } else {
                logger.error("Unknown error with the backend");
            }
            logger.info("Ranger scan: Status code " + response.code() + ", body: " + response.body().string());
            response.close();
        } catch (IOException e) {
            logger.error("Could not connect to backend", e);
        }

    }

    @Override
    public Long getNewPootIdFromBackend() {
        Request request = new Request.Builder()
                .post(RequestBody.create(null, new byte[]{}))
                .url(super.baseUri + "/poten/new")
                .build();
        try {
            Response response = super.client.newCall(request).execute();
            if (response.code() == 201) {
                String body = response.body().string();
                JsonObject jsonObject = (new Gson()).fromJson(body, JsonObject.class);
                return jsonObject.get("pootid").getAsLong();
            } else if (response.code() == 500) {
                super.logger.error("Error while creating new poot");
                return null;
            }
            response.close();
        } catch (IOException e) {
            super.logger.error("Could not connect with backend", e);
        }
        return null; // todo: should not do this

    }

    @Override
    public void sendTemperatureLoggingToBackend(MySetMessage message, Poot poot) {
        logger.debug("Temp logging is not enabled for group 1");
    }

    @Override
    public void sendHumidityLoggingToBackend(MySetMessage message, Poot poot) {
        logger.debug("Humidity  logging is not enabled for group 1");
    }

    @Override
    public void removePootFromBackend(Poot poot) throws NotFoundException {
        ObjectId pootMongoId = this.getMongoIdFromBackend(poot);
        RequestBody requestBody = RequestBody.create(JSON, "{}");
        Request request = new Request.Builder()
                .delete(requestBody)
                .url(this.baseUri + "/poten/" + pootMongoId.toHexString())
                .build();
        try (Response response = super.client.newCall(request).execute()) {
            if (response.code() != 200) {
                logger.warn("Unexpected response code while deleting poot from backend 1: " + response.code());
            }
        } catch (IOException e) {
            logger.error("Error while deleting Poot", e);
        }
    }

    private ObjectId getMongoIdFromBackend(Poot poot) throws NotFoundException {
        String filter = "{\"where\":{\"pootid\":" + poot.getPootid() + "}}";
        Request request = new Request.Builder()
                .url(this.baseUri + "/poten/findOne?filter=" +
                        filter)
                .build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.code() == 200) {
                if (response.body() != null) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = GsonParserUtil.gson.fromJson(responseBody, JsonObject.class);
                    String id = jsonObject.get("id").toString().replaceAll("\"", "");
                    return new ObjectId(id);
                } else {
                    throw new NotFoundException("Could not find backend id");
                }
            } else {
                logger.warn("Unexpected response code while fetching poot from backend 1: " + response.code());
            }
        } catch (IOException e) {
            logger.error("Error while fetching mongoid from backend Poot", e);
        }
        return null;
    }
}
