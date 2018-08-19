package com.n26.statistics.controller;

import com.n26.statistics.exception.TransactionOlderException;
import com.n26.statistics.service.IStatisticsService;
import com.n26.statistics.vo.Statistics;
import com.n26.statistics.vo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController("/transactions")
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    Flux<Transaction> publishTime(@RequestBody @Valid Transaction transaction) throws TransactionOlderException {
        return statisticsService.publishStatistics(transaction);
    }

    @GetMapping
    Statistics statistics(){
        return statisticsService.recoverStatics();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void clearStatics(){
        statisticsService.clearStatics();
    }
}
