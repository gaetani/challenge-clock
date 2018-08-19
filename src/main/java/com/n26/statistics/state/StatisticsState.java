package com.n26.statistics.state;


import com.n26.statistics.vo.Statistics;
import com.n26.statistics.vo.Transaction;
import reactor.core.publisher.Flux;

public interface StatisticsState {

    Flux<Transaction> publishStatistics(Transaction transaction);

    Statistics statistics();
}
