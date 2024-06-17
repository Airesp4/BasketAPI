package Api.Basket_Players.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GerarRelatorio {
    public <T> void gerarArquivo(List<T> dados, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (T dado : dados) {
                writer.write(dado.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
