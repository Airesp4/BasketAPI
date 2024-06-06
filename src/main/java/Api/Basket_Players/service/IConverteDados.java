package Api.Basket_Players.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> obterLista(String json, Class<T> classe);

    <T> T obterProximaPagina(String json) throws JsonProcessingException;
}
