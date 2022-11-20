package ru.sotn.orderservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.service.MapToDto;
import ru.sotn.orderservice.service.OrderService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
//    private final WebClient webClient;
    private final MapToDto mapToDto;
    @PostMapping("/api/order")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderDto> placeOrder(@RequestBody OrderDto orderRequest) {
        CompletableFuture<OrderDto> future =
                CompletableFuture.supplyAsync(() -> mapToDto.orderToDto(orderService.placeOrder(orderRequest)));
        return Mono.fromFuture(future);
    }
    @PostMapping("/api/order/item")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderDto> createNewClothe(@RequestParam(name = "email") String email,
                                          @RequestBody OrderLineItemDto orderLineItemDto){

        CompletableFuture<OrderDto> future =
                CompletableFuture.supplyAsync(()->mapToDto.orderToDto(orderService.addNewClothe(orderLineItemDto,email)));
        Mono<OrderDto> monoFromFuture = Mono.fromFuture(future);
        return monoFromFuture;
    }
    //?
    @DeleteMapping("/api/order/item/delete")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OrderDto> removeClothe(@RequestParam(name = "sku_code") String skuCode,
                                       @RequestParam(name = "email") String email){

        CompletableFuture<OrderDto> future =
                CompletableFuture.supplyAsync(() ->  mapToDto.orderToDto(orderService.removeClothe(skuCode,email)));

        Mono<OrderDto> monoFromFuture = Mono.fromFuture(future);
        return monoFromFuture;
    }
    @PutMapping("/api/order/item/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderDto> addOneQuantityInClothe(@RequestParam(name = "sku_code") String skuCode,
                                                 @RequestParam(name = "email") String email){

        CompletableFuture<OrderDto> future =
                CompletableFuture.supplyAsync(()->mapToDto.orderToDto(orderService.addOneQuantityInClothe(skuCode, email)));
        Mono<OrderDto> monoFromFuture = Mono.fromFuture(future);
        return monoFromFuture;
    }
    @GetMapping("/api/order")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<OrderDto>> getAllOrder() throws ExecutionException, InterruptedException {
//        Flux.create(CompletableFuture.supplyAsync(()->
//                orderService.getAllOrder().stream().map(mapToDto::orderToDto).toList()));
        CompletableFuture<List<OrderDto>> future =
                CompletableFuture.supplyAsync(()->orderService.getAllOrder()
                        .stream().map(mapToDto::orderToDto).collect(Collectors.toList()));


        Mono<List<OrderDto>> monoFromFuture = Mono.fromFuture(future);
        return monoFromFuture;
    }

}
