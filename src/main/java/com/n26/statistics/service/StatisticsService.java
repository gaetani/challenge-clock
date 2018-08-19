package com.n26.statistics.service;

import com.n26.statistics.exception.TransactionOlderException;
import com.n26.statistics.state.StatisticsMachineState;
import com.n26.statistics.state.TransactionsStatisticsState;
import com.n26.statistics.vo.Statistics;
import com.n26.statistics.vo.Transaction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;


@Service
public class StatisticsService implements IStatisticsService {

    private StatisticsMachineState statisticsMachineState = new StatisticsMachineState(new TransactionsStatisticsState());

    @Override
    public Flux<Transaction> publishStatistics(Transaction transaction) throws TransactionOlderException {
        if( transaction.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(60) ) ) throw new TransactionOlderException();
        return statisticsMachineState.getState().publishStatistics(transaction);
    }

    @Override
    public Statistics recoverStatics() {
        return statisticsMachineState.getState().statistics();
    }

    @Override
    public void clearStatics() {
        statisticsMachineState.setState(new TransactionsStatisticsState());
    }


}
