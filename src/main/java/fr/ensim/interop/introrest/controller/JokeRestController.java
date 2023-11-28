package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.Joke;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RestController
public class JokeRestController {

    String messageurl="http://localhost:8080/api/message";
    List<Joke> jokes = new ArrayList<>();
    {
        jokes.add(new Joke(1, "Les plongeurs et le bateau", "Pourquoi les plongeurs plongent-ils toujours en arrière et jamais en avant ? Parce que sinon ils tombent dans le bateau !", 7));
        jokes.add(new Joke(2, "Les chats célèbres", "Comment appelle-t-on un chat qui appartient à une personne célèbre ? Un chat-peau !", 6));
        jokes.add(new Joke(3, "Les plongeurs et les requins", "Pourquoi les plongeurs plongent-ils toujours avec un masque ? Pour ne pas voir les requins dans les yeux !", 8));
        jokes.add(new Joke(4, "Les oiseaux en hiver", "Pourquoi les oiseaux volent-ils vers le sud en hiver ? Parce qu'il fait trop froid pour y aller à pied !", 5));
        jokes.add(new Joke(5, "Les chiens et le sommeil", "Pourquoi les chiens tournent-ils en rond avant de se coucher ? Pour trouver la place la plus confortable !", 9));
        jokes.add(new Joke(6, "Les plongeurs et les dents", "Pourquoi les plongeurs plongent-ils toujours avec des bouteilles d'oxygène ? Parce qu'avec des bouteilles d'eau, ça rouille les dents !", 4));
        jokes.add(new Joke(7, "Les poissons et les yeux", "Pourquoi les poissons ont-ils les yeux dans l'eau ? Parce que s'ils les avaient dans l'air, ils se noieraient !", 7));
        jokes.add(new Joke(8, "Les pingouins et les cravates", "Pourquoi les pingouins portent-ils des cravates ? Parce que ça cache leur cou de poulet !", 6));
        jokes.add(new Joke(9, "Les chats et l'eau", "Pourquoi les chats n'aiment-ils pas l'eau ? Parce que dans l'eau, ils ont des griffes-molles !", 3));
        jokes.add(new Joke(10, "Les plongeurs et les poids", "Pourquoi les plongeurs plongent-ils toujours avec des poids ? Pour être sûrs de remonter !", 8));
    }

    @GetMapping("/joke")
    public ResponseEntity<String> sendJoke(@RequestParam("chatid") Long chatId) {
        RestTemplate restTemplate = new RestTemplate();
        Random rand = new Random();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Joke joke = jokes.get(rand.nextInt(jokes.size()));
        String blagueFormat = "Titre: " + joke.getTitle() + "\n" + "Note: " + joke.getRating() + "/10\n" + "Texte: " + joke.getText();
        String requestBody = "{\"chatId\": " + chatId + ", \"message\": \"" + blagueFormat + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity(messageurl,requestEntity , String.class);
        Logger.getLogger(responseEntity2.getBody());

        return responseEntity2;
    }

}
