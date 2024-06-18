package Api.Basket_Players.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GerarRelatorio {
    private static final String DIRETORIO = "C:\\Users\\pedro\\Desktop\\BasketAPI\\documentos_gerados";

    public <T> void gerarArquivo(List<T> dados, String nomeArquivo) {
        
        String caminhoCompleto = DIRETORIO + File.separator + nomeArquivo;
        
        
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (FileWriter writer = new FileWriter(caminhoCompleto)) {
            for (T dado : dados) {
                writer.write(dado.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
