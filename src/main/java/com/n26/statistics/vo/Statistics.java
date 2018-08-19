package com.n26.statistics.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.statistics.serializer.BigDecimalAmountSerializer;
import lombok.Value;

import java.math.BigDecimal;
import java.math.MathContext;

@Value
public class Statistics {

    @JsonSerialize(using = BigDecimalAmountSerializer.class)
    private BigDecimal sum;
    @JsonSerialize(using = BigDecimalAmountSerializer.class)
    private BigDecimal avg;
    @JsonSerialize(using = BigDecimalAmountSerializer.class)
    private BigDecimal max;
    @JsonSerialize(using = BigDecimalAmountSerializer.class)
    private BigDecimal min;
    private Long count;

    public Statistics add(Transaction transaction){
        BigDecimal amount = sum.add(transaction.getAmount());
        Long countTimes = count + 1;
        return new Statistics(amount,
                              amount.divide(new BigDecimal( countTimes ), 2, BigDecimal.ROUND_HALF_UP),
                              count == 0L? transaction.getAmount(): max.max(transaction.getAmount()),
                              count == 0L? transaction.getAmount(): min.min(transaction.getAmount()),
                              countTimes);
    }

    public Statistics remove(Transaction transaction){
        BigDecimal amount = sum.subtract(transaction.getAmount());
        Long countTimes = count - 1;
        return new Statistics(amount,
                BigDecimal.ZERO.equals(amount) || countTimes == 0 ? BigDecimal.ZERO : amount.divide(new BigDecimal(countTimes), BigDecimal.ROUND_HALF_UP),
                max.max(transaction.getAmount()),
                min.min(transaction.getAmount()),
                countTimes);
    }
}
