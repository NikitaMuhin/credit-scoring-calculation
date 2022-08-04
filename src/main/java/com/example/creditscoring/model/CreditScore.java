package com.example.creditscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScore {

    @Id
    private Long id;

    private Double scoreByAge;
    private Double scoreBySex;
    private Double scoreByCivilState;
    private Double scoreByFamilyMembersNumber;
    private Double scoreByHomePlaceFrom;
    private Double scoreByEducationLevel;
    private Double scoreByContactInformation;
    private Double scoreBySalaryNet;
    private Double scoreByIncomeOtherSources;
    private Double scoreByActivitySector;
    private Double scoreByJobActivityFrom;
    private Double scoreByDelaysDay;
    private Double scoreByDebtAmountMDL;

//    public Double getCreditScoreByPersonalDate() {
//        return ((scoreByAge +
//                scoreBySex +
//                scoreByFamilyMembersNumber +
//                scoreByHomePlaceFrom +
//                scoreByContactInformation +
//                scoreByCivilState +
//                scoreByEducationLevel) * 20) / 100;
//    }

//    public Double getCreditScoreBySourcesIncome() {
//        return ((scoreBySalaryNet +
//                scoreByIncomeOtherSources) * 25) / 100;
//    }
//
//    public Double getCreditScoreBySectorActivity(){
//        return ((scoreByFieldActivity +
//                scoreByExperienceCurrentEmployer) * 15) / 100;
//    }
//
//    public Double getCreditScoreByLoanRepaymentHistory() {
//        return ((scoreByDelaysDay +
//                scoreByDebtAmountMDL) * 40) / 100;
//    }
//
//    public Double getGeneralCreditScore() {
//        return  getCreditScoreByPersonalDate() +
//                getCreditScoreBySourcesIncome() +
//                getCreditScoreBySectorActivity() +
//                getCreditScoreByLoanRepaymentHistory();
//    }

}
