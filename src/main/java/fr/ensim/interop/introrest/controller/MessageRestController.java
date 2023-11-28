package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Chat;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RestController
public class MessageRestController {

	String telegramBotApiKey = "";//insert yours
	@PostMapping("/message")
	public ResponseEntity<String> sendMessage(@RequestBody Long chatId,
											  @RequestBody String message) {
		Message telegramMessage = new Message();
		Chat chatToSendMsg = new Chat();

		chatToSendMsg.setId(chatId);
		telegramMessage.setChat(chatToSendMsg);
		telegramMessage.setText(message);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Message> requestEntity = new HttpEntity<>(telegramMessage, headers);

		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "https://api.telegram.org/bot"+telegramBotApiKey"/sendMessage";
		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
		}

		return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
	}
}
