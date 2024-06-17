package Api.Basket_Players.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Api.Basket_Players.model.DadosJogador;
import Api.Basket_Players.model.DadosTimes;
import Api.Basket_Players.service.ConsumoApi;
import Api.Basket_Players.service.ConverteDados;
import Api.Basket_Players.service.GerarRelatorio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private GerarRelatorio gerador = new GerarRelatorio();

    private final String ENDERECO_PLAYER = "https://nba-stats-db.herokuapp.com/api/playerdata/name/";
    private final String ENDERECO_SEASON = "https://nba-stats-db.herokuapp.com/api/playerdata/season/";

    @GetMapping("/api/filtrarJogador")
    public List<DadosJogador> filtrarJogador(@RequestParam String nomeJogador, @RequestParam boolean relatorio) {
        var json = consumo.obterDados(ENDERECO_PLAYER + nomeJogador.replace(" ", "%20") + "/");
        var filtroJogador =  conversor.obterLista(json, DadosJogador.class);
    
        if (relatorio) {
            gerador.gerarArquivo(filtroJogador, "filtro_jogador" + nomeJogador + ".txt");
        }
    
        return filtroJogador;
    }
    

    @GetMapping("/api/filtrarTemporada")
    public List<DadosJogador> filtrarTemporada(@RequestParam int nTemporada, @RequestParam boolean relatorio) {
        List<DadosJogador> todosJogadoresTemporada = new ArrayList<>();
        try {
            var url = ENDERECO_SEASON + nTemporada + "/";
            String nextPage = url;

            while (nextPage != null) {
                var json = consumo.obterDados(nextPage);
                List<DadosJogador> jogadoresTemporada = conversor.obterLista(json, DadosJogador.class);
                todosJogadoresTemporada.addAll(jogadoresTemporada);
                nextPage = conversor.obterProximaPagina(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (relatorio) {
            gerador.gerarArquivo(todosJogadoresTemporada, "filtro_jogadoresTemporada" + nTemporada + ".txt");
        }

        return todosJogadoresTemporada;
    }

    @GetMapping("/api/filtrarJogadorAno")
    public List<DadosJogador> filtrarJogadorAno(@RequestParam String nomeJogador, @RequestParam int nTemporada, @RequestParam boolean relatorio) {
        List<DadosJogador> jogadorFiltrado = new ArrayList<>();
        try {
            var json = consumo.obterDados(ENDERECO_PLAYER + nomeJogador.replace(" ", "%20") + "/");
            List<DadosJogador> jogadorAno = conversor.obterLista(json, DadosJogador.class);

            jogadorFiltrado = jogadorAno.stream()
                    .filter(j -> j.temporada() == nTemporada)
                    .collect(Collectors.toList());

            if (jogadorFiltrado.isEmpty()) {
                throw new Exception("Ano não disponível para o jogador escolhido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (relatorio) {
            gerador.gerarArquivo(jogadorFiltrado, "filtro_jogadorFiltrado" + nomeJogador + nTemporada + ".txt");
        }

        return jogadorFiltrado;
    }

    @GetMapping("/api/filtrarJogadoresTime")
    public List<DadosTimes> filtrarJogadoresTime(@RequestParam int nTemporada, @RequestParam String siglaTime, @RequestParam boolean relatorio) {
        List<DadosTimes> jogadoresTime = new ArrayList<>();
        try {
            var url = ENDERECO_SEASON + nTemporada + "/";
            String nextPage = url;

            while (nextPage != null) {
                var json = consumo.obterDados(nextPage);
                List<DadosTimes> times = conversor.obterLista(json, DadosTimes.class);

                jogadoresTime.addAll(times.stream()
                        .filter(d -> d.time().equalsIgnoreCase(siglaTime))
                        .collect(Collectors.toList()));

                nextPage = conversor.obterProximaPagina(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (relatorio) {
            gerador.gerarArquivo(jogadoresTime, "filtro_jogadoresTime" + siglaTime + nTemporada + ".txt");
        }

        return jogadoresTime;
    }
}