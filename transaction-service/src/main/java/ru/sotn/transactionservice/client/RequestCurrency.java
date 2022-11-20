package ru.sotn.transactionservice.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.model.CurrencyResponse;

@Component

public class RequestCurrency {
    private final WebClient webClient;

    public RequestCurrency(WebClient.Builder webClientBuilder) {
        this .webClient = webClientBuilder.baseUrl( "https://api.apilayer.com" ).build();
    }


    public Mono<CurrencyResponse> getConvertCurrency(String to, String from, String amount){
        return webClient.get().uri("/currency_data/convert",
                 uriBuilder -> uriBuilder.queryParam("to", to)
                         .queryParam("from", from)
                         .queryParam("amount", amount)
                         .build()).accept(MediaType.APPLICATION_JSON)
                .header("apikey", "xFRqciqIsn38vEQWfdHdNcWJYsW06KL3")
                .retrieve().bodyToMono(CurrencyResponse.class);
//        restTemplate = new RestTemplate();
//        String url = "https://api.apilayer.com/currency_data/convert?to={to}&from={from}&amount={amount}";
//        Map<String, String> param = new HashMap<>();
//        param.put("to", to);
//        param.put("from", from);
//        param.put("amount", amount);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("apikey", "xFRqciqIsn38vEQWfdHdNcWJYsW06KL3");
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<CurrencyResponse> response = restTemplate.exchange(url,
//                HttpMethod.GET, requestEntity, CurrencyResponse.class, param);
//        CurrencyResponse res = response.getBody();
//        return res;
    }
}
