package Api.Basket_Players;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Api.Basket_Players.principal.Principal;

@SpringBootApplication
public class BasketPlayersApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BasketPlayersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
	}

}
