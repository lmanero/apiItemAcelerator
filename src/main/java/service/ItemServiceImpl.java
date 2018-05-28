package service;

import com.google.gson.Gson;
import domain.Item;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Collection;

public class ItemServiceImpl implements ItemService{

    RestHighLevelClient client;
    String index = "tpitem";
    String type = "items";

    public ItemServiceImpl() {
        client =new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    @Override
    public Collection<Item> getItems() {
        return null;
    }

    @Override
    public Item getItem(String id) {
        Item response = null;
        GetRequest getRequest = new GetRequest(index, type, id);
        try {
            GetResponse elasticResponse = client.get(getRequest);
            response = new Gson().fromJson(elasticResponse.getSourceAsString() , Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Item updateItem(Item forEdit){
        try {
            if (forEdit.getId() == null)
                throw new Exception();

            UpdateRequest request = new UpdateRequest(index, type, forEdit.getId());
            request.doc(new Gson().toJson(forEdit), XContentType.JSON);
            UpdateResponse updateResponse;
            try {
                updateResponse = client.update(request);
                System.out.println(updateResponse.getResult());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return forEdit;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void deleteItem(String id) {
        DeleteRequest request = new DeleteRequest(index, type, id);
        try {
            DeleteResponse deleteResponse = client.delete(request);
            System.out.println(deleteResponse.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addItem(Item item) {

        IndexRequest request = new IndexRequest(index, type, item.getId());
        request.source(new Gson().toJson(item), XContentType.JSON);
        IndexResponse indexResponse;
        try {
            indexResponse = client.index(request);
            System.out.println(indexResponse.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
