package com.n26.statistics.state;


import com.n26.statistics.vo.Statistics;
import com.n26.statistics.vo.Transaction;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TransactionsStatisticsState implements StatisticsState {
    private static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal(BigInteger.ZERO, 2);
    private volatile Statistics statistics;

    public Flux<Transaction> publishStatistics(Transaction transaction){
        return Flux.just(transaction)
                .map(this::addStatistics).doAfterTerminate(()-> dropElement(transaction));
    }

    private synchronized Transaction addStatistics(Transaction immutableTransaction){
        this.statistics = statistics.add(immutableTransaction);
        return immutableTransaction;
    }

    private synchronized Transaction removeStatistics(Transaction immutableTransaction){
        this.statistics = statistics.remove(immutableTransaction);
        return immutableTransaction;
    }

    private Disposable dropElement(Transaction transaction) {
        Duration duration = Duration.between(transaction.getTimestamp(), LocalDateTime.now());
        long seconds = 60 - duration.getSeconds();
        return Mono.just(transaction).delayElement(Duration.of(seconds, SECONDS))
                .map(this::removeStatistics).subscribe();
    }


    public TransactionsStatisticsState(){
        statistics = new Statistics(BIGDECIMAL_ZERO, BIGDECIMAL_ZERO, BIGDECIMAL_ZERO, BIGDECIMAL_ZERO, 0L);
    }


    public Statistics statistics(){
        return this.statistics;
    }
}
