package com.example.mongodemo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Locale;

@Component
@Slf4j
@EnableScheduling
public class CallServiceSchedule {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 600000) // 600000 milliseconds = 10 minutes
    public void callService() {
        HttpEntity<String> entity = createHttpEntity();

        try {
            // call api
            ResponseEntity<String> response = restTemplate.exchange("https://call-service.onrender.com/call", HttpMethod.GET, entity, String.class);
            String res = response.getBody();
            log.info(" Call successfully : {}", res);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private HttpEntity<String> createHttpEntity() {
        // create headers include token
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptLanguageAsLocales(Collections.singletonList(Locale.forLanguageTag("vi")));
        return new HttpEntity<>("body", headers);
    }
}
