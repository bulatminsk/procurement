package by.htp.procurement.entity;

import java.util.Objects;

public class Evaluation extends Entity implements Comparable<Evaluation> {

    private Integer id;
    private String criteria;
    private Integer weight;
    private Boolean isArchived;
    private Integer maxScore;

    /*Many to one*/
    private Tender tender;

    public Evaluation() {
    }

    public Evaluation(Integer id, String criteria, Integer weight, Boolean isArchived, Integer maxScore, Tender tender) {
        this.id = id;
        this.criteria = criteria;
        this.weight = weight;
        this.isArchived = isArchived;
        this.maxScore = maxScore;
        this.tender = tender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.criteria);
        hash = 37 * hash + Objects.hashCode(this.weight);
        hash = 37 * hash + Objects.hashCode(this.isArchived);
        hash = 37 * hash + Objects.hashCode(this.maxScore);
        hash = 37 * hash + Objects.hashCode(this.tender);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evaluation other = (Evaluation) obj;
        if (!Objects.equals(this.criteria, other.criteria)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        if (!Objects.equals(this.isArchived, other.isArchived)) {
            return false;
        }
        if (!Objects.equals(this.maxScore, other.maxScore)) {
            return false;
        }
        if (!Objects.equals(this.tender, other.tender)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evaluation{" + "id=" + id + ", criteria=" + criteria + ", weight=" + weight + ", isArchived=" + isArchived + ", maxScore=" + maxScore + ", tender=" + tender + '}';
    }

    @Override
    public int compareTo(Evaluation evaluation) {
        return Integer.compare(id, evaluation.id);
    }

}
