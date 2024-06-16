package Api.Basket_Players.principal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Api.Basket_Players.model.DadosJogador;
import Api.Basket_Players.model.DadosTimes;
import Api.Basket_Players.service.ConsumoApi;
import Api.Basket_Players.service.ConverteDados;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO_PLAYER = "https://nba-stats-db.herokuapp.com/api/playerdata/name/";
    private final String ENDERECO_SEASON = "https://nba-stats-db.herokuapp.com/api/playerdata/season/";

    @GetMapping("/api/filtrarJogador")
    public List<DadosJogador> filtrarJogador(@RequestParam String nomeJogador) {
        var json = consumo.obterDados(ENDERECO_PLAYER + nomeJogador.replace(" ", "%20") + "/");
        return conversor.obterLista(json, DadosJogador.class);
    }

    @GetMapping("/api/filtrarTemporada")
    public List<DadosJogador> filtrarTemporada(@RequestParam int nTemporada) {
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
        return todosJogadoresTemporada;
    }

    @GetMapping("/api/filtrarJogadorAno")
    public List<DadosJogador> filtrarJogadorAno(@RequestParam String nomeJogador, @RequestParam int nTemporada) {
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
        return jogadorFiltrado;
    }

    @GetMapping("/api/filtrarTime")
    public Set<String> filtrarTime(@RequestParam int nTemporada) {
        Set<String> timesUnicos = new HashSet<>();
        try {
            var url = ENDERECO_SEASON + nTemporada + "/";
            String nextPage = url;

            while (nextPage != null) {
                var json = consumo.obterDados(nextPage);
                List<DadosTimes> times = conversor.obterLista(json, DadosTimes.class);

                times.forEach(t -> timesUnicos.add(t.time()));
                nextPage = conversor.obterProximaPagina(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timesUnicos;
    }

    @GetMapping("/api/filtrarJogadoresTime")
    public List<DadosTimes> filtrarJogadoresTime(@RequestParam int nTemporada, @RequestParam String siglaTime) {
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
        return jogadoresTime;
    }
}
