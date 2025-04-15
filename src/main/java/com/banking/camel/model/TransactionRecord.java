package com.banking.camel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionRecord implements Serializable {

    public String transactionId;
    public String fromAccount;
    public String toAccount;
    public double amount;
    public String currency;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime timestamp;

    public TransactionRecord() {}

    public TransactionRecord(String transactionId, String fromAccount, String toAccount, double amount, String currency, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Transaction[%s: %s -> %s, %.2f %s, at %s]",
                transactionId, fromAccount, toAccount, amount, currency, timestamp);
    }
}
