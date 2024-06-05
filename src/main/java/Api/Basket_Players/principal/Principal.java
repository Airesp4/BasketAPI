package Api.Basket_Players.principal;

import java.util.List;
import java.util.Scanner;

import Api.Basket_Players.model.DadosJogador;
import Api.Basket_Players.service.ConsumoApi;
import Api.Basket_Players.service.ConverteDados;


public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://nba-stats-db.herokuapp.com/api/playerdata/name/";
    private final String MENU ="""
        \n*******************************************
        Escolha uma opção\n
        1 - Filtrar Jogador
        2 - Filtrar por ano
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
                case 0:
                    System.out.println("O programa foi finalizado!");
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
        
        var json = consumo.obterDados(ENDERECO + nomeJogador.replace(" ", "%20") + "/");

        List<DadosJogador> dadosJogador = conversor.obterLista(json, DadosJogador.class);

        dadosJogador.forEach(System.out::println);
    }
}
