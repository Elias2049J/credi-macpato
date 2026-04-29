package com.runaitec.credimacpato.dto.report;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CashClosureReport.class, name = "CASH_CLOSURE"),
        @JsonSubTypes.Type(value = CustomerDebtsReport.class, name = "CUSTOMER_DEBTS")
})
public abstract class Report {
    private LocalDateTime createdAt;
}
