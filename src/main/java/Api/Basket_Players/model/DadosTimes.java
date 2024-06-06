package Api.Basket_Players.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTimes(@JsonAlias("id") Integer id,
                         @JsonAlias("team") String time,
                         @JsonAlias("player_name") String nome,
                         @JsonAlias("season") Integer temporada) {

    @Override
    public final String toString() {
        
        return "Time: " + time + " ID_Jogador: " + id
        + " Nome: " + nome;
    }
}
