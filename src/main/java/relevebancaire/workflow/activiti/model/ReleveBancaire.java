package relevebancaire.workflow.activiti.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public class ReleveBancaire{


    private Long releveBancaireId;
    private Date dateReception;
    private String label;
    private int nbrLignes;
    private int nbrOperationCredit;
    private int nbrOperationDebit;
    private BigDecimal soldeInitial;
    private BigDecimal soleFinal;
    private List<LigneReleve> lignereleve = new ArrayList<>();    // ONE relevebancaire has MANY lignereleve (One to Many)

    public ReleveBancaire() {
    }

    public ReleveBancaire(Long releveBancaireId, Date dateReception, String label, int nbrLignes, int nbrOperationCredit, int nbrOperationDebit, BigDecimal soldeInitial, BigDecimal soleFinal, List<LigneReleve> lignereleve) {
        this.releveBancaireId = releveBancaireId;
        this.dateReception = dateReception;
        this.label = label;
        this.nbrLignes = nbrLignes;
        this.nbrOperationCredit = nbrOperationCredit;
        this.nbrOperationDebit = nbrOperationDebit;
        this.soldeInitial = soldeInitial;
        this.soleFinal = soleFinal;
        this.lignereleve = lignereleve;
    }

    public ReleveBancaire(String releveBancaire) {
        // handle url
    }

    public Long getReleveBancaireId() {
        return releveBancaireId;
    }

    public void setReleveBancaireId(Long releveBancaireId) {
        this.releveBancaireId = releveBancaireId;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNbrLignes() {
        return nbrLignes;
    }

    public void setNbrLignes(int nbrLignes) {
        this.nbrLignes = nbrLignes;
    }

    public int getNbrOperationCredit() {
        return nbrOperationCredit;
    }

    public void setNbrOperationCredit(int nbrOperationCredit) {
        this.nbrOperationCredit = nbrOperationCredit;
    }

    public int getNbrOperationDebit() {
        return nbrOperationDebit;
    }

    public void setNbrOperationDebit(int nbrOperationDebit) {
        this.nbrOperationDebit = nbrOperationDebit;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public BigDecimal getSoleFinal() {
        return soleFinal;
    }

    public void setSoleFinal(BigDecimal soleFinal) {
        this.soleFinal = soleFinal;
    }

    public List<LigneReleve> getLignereleve() {
        return lignereleve;
    }

    public void setLignereleve(List<LigneReleve> lignereleve) {
        if (this.getLignereleve() == null){
            throw new IllegalArgumentException("cette exception vient du setterLigneReleve du class ReleveBancaire [Domain]");

        }
        this.lignereleve = lignereleve;
    }
}


