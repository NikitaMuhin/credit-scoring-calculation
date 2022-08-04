package com.example.creditscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentHistory {

    @Id
    private Long id;
    private Integer creditId;
    private BigDecimal invested;
    private BigDecimal indebtedness;
    private Date paymentDate;

    public RepaymentHistory(Integer creditId, BigDecimal invested, BigDecimal indebtedness, Date paymentDate) {
        this.creditId = creditId;
        this.invested = invested;
        this.indebtedness = indebtedness;
        this.paymentDate = paymentDate;
    }
}
