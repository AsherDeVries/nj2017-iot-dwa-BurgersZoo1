package nl.han.gateway.dao.mongodb;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import nl.han.gateway.dao.GsonParserUtil;
import nl.han.gateway.dao.IMyMessagesDAO;
import nl.han.gateway.exceptions.NotImplementedException;
import nl.han.mysensor.models.MyMessage;
import nl.han.mysensor.service.MySensorReceiveService;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class MySensorMongoDAO implements IMyMessagesDAO {

    private MongoCollection<Document> collection;
    private Gson gson;
    private static Logger logger = LoggerFactory.getLogger(MySensorReceiveService.class.getName());

    MySensorMongoDAO() {
        this.collection = MongodbConnector.instance().getDatabase().getCollection("mymessages");
        this.gson = GsonParserUtil.gson;
    }

    @Override
    public MyMessage save(MyMessage entity) {
        Document doc = Document.parse(gson.toJson(entity));
        this.collection.insertOne(doc);
        return this.get((ObjectId) doc.get("_id"));
    }

    @Override
    public MyMessage update(MyMessage entity) {
        Document doc = new Document("$set",
                Document.parse(gson.toJson(entity))
                        .append("class",
                                entity.getClass()
                                        .getCanonicalName()));
        this.collection.updateOne(
                eq("_id", entity.getId()),
                doc);
        return this.getMyMessageFromDocument((Document) doc.get("$set"));
    }

    @Override
    public List<MyMessage> getAll() {
        List<MyMessage> messageList = new ArrayList<>();
        this.collection.find().forEach((Consumer<? super Document>) document -> {
            MyMessage message = getMyMessageFromDocument(document);
            messageList.add(message);
        });
        return messageList;
    }

    @Override
    public MyMessage get(MyMessage entity) {
        MyMessage message = null;
        if (entity.getId() != null) {
            message = this.get(entity.getId());
        }
        return message;
    }

    @Override
    public MyMessage get(ObjectId objectId) {
        return this.getMyMessageFromDocument(this.collection.find(eq("_id", objectId)).first());
    }

    @Override
    public List<MyMessage> getAllFiltered(String[] searchParams, int page, int size, String order) {
        throw new NotImplementedException("Not yet implemented");
//        Sort sort = null;
//        if ("DESC".equals(order)) {
//            sort = Sort.descending("$oid");
//
//        } else if ("ASC".equals(order)) {
//            sort = Sort.ascending("");
//        }
//        Document filter = new Document();
//        filter.append("")
//        this.collection.find();
//
//        Arrays.stream(searchParams)
//                .map(s -> s.split(":")).forEach(s -> {
//            eq(s[0], s[1]);
//        });
//        Bson filter = eq("", "");
//        return null;
    }


    @Override
    public void delete(MyMessage entity) {
        this.collection.findOneAndDelete(eq("_id", entity.getId()));
    }


    /**
     * Parse a document to a MyMessage instance.
     *
     * @param document
     * @return MyMessage instance (a subclass that is)
     */
    private MyMessage getMyMessageFromDocument(Document document) {
        MyMessage message = null;
        try {
            message = (MyMessage) gson.fromJson(document.toJson(), Class.forName(String.valueOf(document.get("class"))));
        } catch (ClassNotFoundException e) {
            logger.error("Could not cast given document to object", e);
        }
        return message;
    }
}
