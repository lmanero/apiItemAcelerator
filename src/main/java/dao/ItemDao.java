package dao;

import com.google.gson.Gson;
import domain.Item;
import exception.ElasticException;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ItemDao {

    private RestHighLevelClient client;
    final String INDEX = "tpitem";
    final String TYPE = "items";

    public ItemDao() {
        client =new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    public Collection<Item> getItems() throws ElasticException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.types(TYPE);
        SearchResponse searchResponse;
        ArrayList<Item> response = new ArrayList<>();
        try {
            searchResponse = client.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                Item item = new Gson().fromJson(hit.getSourceAsString(), Item.class);
                item.setId(hit.getId());
                response.add(item);
            }
            return response;
        } catch (IOException e) {
            throw new ElasticException("Error al conectar con elasticSearch");
        }catch(ElasticsearchException e) {
            throw new ElasticException("Error al intentar obtener los items");
        }
    }


    public Item getItem(String id) throws ElasticException {
        Item response = null;
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        try {
            GetResponse elasticResponse = client.get(getRequest);
            response = new Gson().fromJson(elasticResponse.getSourceAsString() , Item.class);
            if (elasticResponse.isExists()) response.setId(elasticResponse.getId());
            return response;
        } catch (IOException e) {
            throw new ElasticException("Error al conectar con elasticSearch");
        }catch(ElasticsearchException e) {
            throw new ElasticException("Error al intentar obtener el item");
        }
    }

    public Item updateItem(Item forEdit) throws ElasticException {
        UpdateRequest request = new UpdateRequest(INDEX, TYPE, forEdit.getId());
        request.doc(new Gson().toJson(forEdit), XContentType.JSON);
        UpdateResponse updateResponse;
        try {
            updateResponse = client.update(request);
            return forEdit;
        } catch (IOException e) {
            throw new ElasticException("Error al conectar con elasticSearch");
        }catch(ElasticsearchException e) {
            throw new ElasticException("Error al intentar actualizar el item");
        }
    }


    public void deleteItem(String id) throws ElasticException {
        DeleteRequest request = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = client.delete(request);
        } catch (IOException e) {
            throw new ElasticException("Error al conectar con elasticSearch");
        } catch(ElasticsearchException e) {
            throw new ElasticException("Error al intentar eliminar el item");
        }

    }


    public void addItem(Item item) throws ElasticException {
        IndexRequest request = new IndexRequest(INDEX, TYPE);
        request.source(new Gson().toJson(item), XContentType.JSON);
        IndexResponse indexResponse;
        try {
            indexResponse = client.index(request);
        } catch (IOException e) {
            throw new ElasticException("Error al conectar con elasticSearch");
        }catch(ElasticsearchException e) {
            throw new ElasticException("Error al intentar registrar el item");
        }
    }

}
