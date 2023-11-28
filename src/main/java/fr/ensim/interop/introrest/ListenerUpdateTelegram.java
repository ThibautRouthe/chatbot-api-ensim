package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.controller.MessageRestController;
import fr.ensim.interop.introrest.meteo.OpenWeather;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");
		MessageRestController sender = new MessageRestController();
		String telegramBotApiKey = "";//insert yours
		String uri = "https://api.telegram.org/bot"+telegramBotApiKey+"/getUpdates";
		String messageurl="http://localhost:8080/api/message";
		String jokeurl="http://localhost:8080/api/joke";
		Long offset = null;

		while(true) {
			if(offset!=null){
				uri.concat("?offset="+offset);
			}
			ApiResponseUpdateTelegram lastUpdate = restTemplate.getForObject(uri, ApiResponseUpdateTelegram.class);
			System.out.println("lastUpdate = " + lastUpdate);
			List<Update> updates = lastUpdate.getResult();
			offset= Long.valueOf(updates.get(updates.size()-1).getUpdateId());

			for (Update up : updates) {
				if (up.getMessage().getText().charAt(0) == '/') {
					String[] word = up.getMessage().getText().split(" ");
					switch (word[0]) {
						case "meteo":
							String city = word[1];
							city.replace(" ","+");
							ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(jokeurl + "?chatid="+up.getMessage().getChatId()+"&city="+city, null, String.class);
							Logger.getLogger(responseEntity1.getBody());
							break;
						case "blague":
							ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(jokeurl + "?chatid="+up.getMessage().getChatId(), null, String.class);
							Logger.getLogger(responseEntity2.getBody());
							break;
						default:
							String message = "commande non reconnue. Commandes disponibles : /meteo [Ville] , /blague";
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							String requestBody = "{\"chatId\": " + up.getMessage().getChatId() + ", \"message\": \"" + message + "\"}";
							HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
							ResponseEntity<String> responseEntity3 = restTemplate.postForEntity(messageurl, requestEntity, String.class);
							Logger.getLogger(responseEntity3.getBody());
					}
				}
			}
		}
		// Operation de pooling pour capter les evenements Telegram
	}
}
