package com.banking.camel.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@XmlRootElement(name = "TransactionHistoryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Slf4j
@ToString
public class TransactionHistoryResponse {

    @XmlElement(name = "transaction")
    private List<TransactionRecord> transactions;

    public TransactionHistoryResponse() {
        this.transactions = new ArrayList<>();
    }

    public TransactionHistoryResponse(List<TransactionRecord> transactions) {
        log.info("Constructing History : {}", transactions);
        this.transactions = transactions;
    }

}
