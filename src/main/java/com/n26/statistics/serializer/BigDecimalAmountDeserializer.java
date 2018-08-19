package com.n26.statistics.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalAmountDeserializer extends JsonDeserializer<BigDecimal> {

    private NumberDeserializers.BigDecimalDeserializer delegate = NumberDeserializers.BigDecimalDeserializer.instance;

    @Override
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        BigDecimal bd = delegate.deserialize(jp, ctxt);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

}
