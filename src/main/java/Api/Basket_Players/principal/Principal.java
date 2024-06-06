package Api.Basket_Players.principal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import Api.Basket_Players.model.DadosJogador;
import Api.Basket_Players.model.DadosTimes;
import Api.Basket_Players.service.ConsumoApi;
import Api.Basket_Players.service.ConverteDados;


public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO_PLAYER = "https://nba-stats-db.herokuapp.com/api/playerdata/name/";
    private final String ENDERECO_SEASON = "https://nba-stats-db.herokuapp.com/api/playerdata/season/";
    private final String MENU ="""
        \n*******************************************
        Escolha uma opção\n
        1 - Filtrar Jogador
        2 - Filtrar por ano
        3 - Jogador p/ ano
        4 - Filtrar times
        0 - Finalizar pesquisa
        \n*******************************************
        """;

    public void exibirMenu(){

        var opcao = -1;
        while (opcao != 0) {
            System.out.println(MENU);
            var escolha = teclado.nextInt();

            teclado.nextLine();

            switch (escolha) {
                case 1:
                    filtrarJogador();
                    break;
                case 2:
                    filtrarTemporada();
                    break;
                case 3:
                    filtrarJogadorAno();
                    break;
                case 4:
                    filtrarTime();
                    break;
                case 0:
                    System.out.println("O programa foi finalizado!");
                    opcao = 0;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    public void filtrarJogador(){
        System.out.println("Digite o nome de um jogador: ");
        var nomeJogador = teclado.nextLine();
        
        var json = consumo.obterDados(ENDERECO_PLAYER + nomeJogador.replace(" ", "%20") + "/");

        List<DadosJogador> dados_jogador = conversor.obterLista(json, DadosJogador.class);

        dados_jogador.forEach(System.out::println);
    }

    public void filtrarTemporada() {
        System.out.println("Digite o ano da temporada: ");
        var nTemporada = teclado.nextInt();
    
        try {
            var url = ENDERECO_SEASON + nTemporada + "/";
        
            List<DadosJogador> todosJogadoresTemporada = new ArrayList<>();

            String nextPage = url;

            while (nextPage != null) {

                var json = consumo.obterDados(nextPage);

                List<DadosJogador> jogadoresTemporada = conversor.obterLista(json, DadosJogador.class);
                todosJogadoresTemporada.addAll(jogadoresTemporada);

                nextPage = conversor.obterProximaPagina(json);
            }

            todosJogadoresTemporada.forEach(System.out::println);
        
        } catch (Exception e) {
            System.out.println("Erro ao filtrar temporada: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void filtrarJogadorAno() {
        System.out.println("Digite o nome de um jogador: ");
        var nomeJogador = teclado.nextLine();

        System.out.println("Digite o ano da temporada: ");
        var nTemporada = teclado.nextInt();

        var json = consumo.obterDados(ENDERECO_PLAYER + nomeJogador.replace(" ", "%20") + "/");
        List<DadosJogador> jogadorAno = conversor.obterLista(json, DadosJogador.class);

        jogadorAno.stream()
                  .filter(j -> j.temporada() == nTemporada)
                  .forEach(System.out::println);
    }

    public void filtrarTime() {
        var nTemporada = 2023;
        Set<String> timesUnicos = new HashSet<>();
    
        try {
            var url = ENDERECO_SEASON + nTemporada + "/";
        
            List<DadosTimes> todosTimes = new ArrayList<>();
            String nextPage = url;
    
            while (nextPage != null) {
                var json = consumo.obterDados(nextPage);
                List<DadosTimes> times = conversor.obterLista(json, DadosTimes.class);
    
                times.forEach(t -> timesUnicos.add(t.time()));
    
                todosTimes.addAll(times);
    
                nextPage = conversor.obterProximaPagina(json);
            }
    
            timesUnicos.forEach(System.out::println);
    
            System.out.println("Digite a sigla de um time para ver os jogadores (ex: HOU): ");
            var siglaTime = teclado.nextLine().toUpperCase();
    
            List<DadosTimes> jogadoresTime = todosTimes.stream()
                                    .filter(d -> d.time().equalsIgnoreCase(siglaTime))
                                    .collect(Collectors.toList());
    
            jogadoresTime.forEach(System.out::println);
        
        } catch (Exception e) {
            System.out.println("Erro ao listar times da temporada 2023: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
