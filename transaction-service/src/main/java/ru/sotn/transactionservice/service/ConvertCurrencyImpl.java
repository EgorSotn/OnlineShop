package ru.sotn.transactionservice.service;


import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.Cacheable;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.client.RequestCurrency;
import ru.sotn.transactionservice.model.dto.CurrencyResponse;


@Service
public class ConvertCurrencyImpl implements ConvertCurrency{
    private final RequestCurrency requestCurrency;

    public ConvertCurrencyImpl(RequestCurrency requestCurrency){
        this.requestCurrency = requestCurrency;

    }

    @Override
    @Cacheable(value = "convert",key = "{#to, #from}")
    public Mono<CurrencyResponse> convertCurrency(String to, String from, String amount) {

        return requestCurrency.getConvertCurrency(to, from, amount);
    }


    @CacheEvict(value = "convert", allEntries = true)
    @Scheduled(cron = "${scheduler.cron.expression}")
    public void updateCache(){
        System.out.println("=> вызвано очистка кэша");
    }


}
