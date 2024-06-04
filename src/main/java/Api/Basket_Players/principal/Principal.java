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

    public void exibirMenu(){
        System.out.println("Digite o nome de um jogador: ");
        var nomeJogador = teclado.nextLine();
        
        var json = consumo.obterDados(ENDERECO + nomeJogador.replace(" ", "%20") + "/");
        System.out.println(json);
        List<DadosJogador> dadosJogador = conversor.obterLista(json, DadosJogador.class);

        dadosJogador.forEach(System.out::println);

    }
}
