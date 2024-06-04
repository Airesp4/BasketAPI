package Api.Basket_Players.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosJogador(@JsonAlias("id") Integer id,
                           @JsonAlias("player_name") String nome,
                           @JsonAlias("age") Integer idade,
                           @JsonAlias("minutes_played") Integer minutosJogados,
                           @JsonAlias("PTS") Integer pontos,
                           @JsonAlias("team") String time,
                           @JsonAlias("season") Integer temporada) {

}
