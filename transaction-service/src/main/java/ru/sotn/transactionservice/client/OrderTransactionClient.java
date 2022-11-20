package ru.sotn.transactionservice.client;




import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import ru.sotn.transactionservice.config.JwtContext;
import ru.sotn.transactionservice.model.CurrencyResponse;
import ru.sotn.transactionservice.model.OrderDto;

//@ReactiveFeignClient("order-service")
@Component

public class OrderTransactionClient {
//    @PostMapping("/transaction")
    private final JwtContext jwtContext;
    private final WebClient webClient;


    public OrderTransactionClient(@Value("${service.order}") String orderServiceUri, WebClient.Builder webClientBuilder, JwtContext jwtContext) {
        this.jwtContext = jwtContext;
        this.webClient = webClientBuilder.baseUrl(String.format("http://%s/", orderServiceUri)).build();
    }

   public  Mono<OrderDto> getOrderInTransaction( String email){
        return webClient.post().uri("transaction",
                        uriBuilder -> uriBuilder
                                .queryParam("email", email)
                                .build()).accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + jwtContext.jwtToken.getTokenValue())
                .retrieve().bodyToMono(OrderDto.class);
    }
}
