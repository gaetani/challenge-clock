package com.n26.statistics.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.statistics.serializer.BigDecimalAmountDeserializer;
import com.n26.statistics.serializer.BigDecimalAmountSerializer;
import com.n26.statistics.serializer.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @NotNull
    @JsonSerialize(using = BigDecimalAmountSerializer.class)
    @JsonDeserialize(using = BigDecimalAmountDeserializer.class)
    private BigDecimal amount;

    @NotNull @PastOrPresent
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;


}
