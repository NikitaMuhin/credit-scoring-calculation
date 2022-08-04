package com.example.creditscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credits {

    @Id
    private Long id;
    private Integer userId;
    private Integer typeId;
    private Integer currencyId;
    private Float percentage;
    private BigDecimal initialValue;
    private BigDecimal totalValue;
    private Date expectedPaymentDate;
    private BigDecimal expectedPaymentValue;
    private Date creditFrom;
    private Date creditTo;

    public Credits(Integer userId, Integer typeId, Integer currencyId, Float percentage,
                  BigDecimal initialValue, BigDecimal totalValue,
                  Date expectedPaymentDate, BigDecimal expectedPaymentValue, Date creditFrom, Date creditTo) {
        this.userId = userId;
        this.typeId = typeId;
        this.currencyId = currencyId;
        this.percentage = percentage;
        this.initialValue = initialValue;
        this.totalValue = totalValue;
        this.expectedPaymentDate = expectedPaymentDate;
        this.expectedPaymentValue = expectedPaymentValue;
        this.creditFrom = creditFrom;
        this.creditTo = creditTo;
    }
}
