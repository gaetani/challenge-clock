package com.n26.statistics.service;

import com.n26.statistics.exception.TransactionOlderException;
import com.n26.statistics.vo.Statistics;
import com.n26.statistics.vo.Transaction;
import reactor.core.publisher.Flux;

public interface IStatisticsService {
    Flux<Transaction> publishStatistics(Transaction transaction) throws TransactionOlderException;

    Statistics recoverStatics();

    void clearStatics();
}
