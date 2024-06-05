package Api.Basket_Players.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode resultsNode = rootNode.path("results");
            
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, classe);

            return mapper.readValue(resultsNode.toString(), listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
