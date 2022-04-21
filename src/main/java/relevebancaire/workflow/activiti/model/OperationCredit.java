package relevebancaire.workflow.activiti.model;

import java.util.Date;

public class OperationCredit {

    private Long operationCreditId;
    private Date operationDate;
    private LigneReleve ligneReleve;

    public OperationCredit(Long operationCreditId, Date operationDate, LigneReleve ligneReleve) {
        this.operationCreditId = operationCreditId;
        this.operationDate = operationDate;
        this.ligneReleve = ligneReleve;
    }

    public OperationCredit() {
    }


    public Long getOperationCreditId() {
        return operationCreditId;
    }

    public void setOperationCreditId(Long operationCreditId) {
        this.operationCreditId = operationCreditId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public LigneReleve getLigneReleve() {
        return ligneReleve;
    }

    public void setLigneReleve(LigneReleve ligneReleve) {
        this.ligneReleve = ligneReleve;
    }
}
