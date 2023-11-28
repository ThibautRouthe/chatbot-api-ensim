package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.meteo.OpenWeather;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class MeteoRestController {

    String openWeatherMapApiId = ""//insert yours
    String messageurl="http://localhost:8080/api/message";
    @GetMapping("/meteo")
    public ResponseEntity<String> sendMeteo(@RequestParam("chatid") Long chatId, @RequestParam("city") String city){
        RestTemplate restTemplate = new RestTemplate();
        OpenWeather openWeather = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+openWeatherMapApiId,OpenWeather.class);
        String messagemeteo = openWeather.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"chatId\": " + chatId + ", \"message\": \"" + messagemeteo + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity(messageurl, requestEntity, String.class);
        Logger.getLogger(responseEntity1.getBody());

        return responseEntity1;
    }
}
