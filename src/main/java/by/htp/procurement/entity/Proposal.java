package by.htp.procurement.entity;

import java.util.Date;
import java.util.Objects;

public class Proposal extends Entity {

    private Integer id;
    private Date appliedAt;
    private String application;
    private String filePath;
    private Boolean isArchived;

    /*Many to one*/
    private Tender tender;

    /*Many to one*/
    private User user;

    public Proposal() {
    }

    public Proposal(Integer id, Date appliedAt, String application, String filePath, Boolean isArchived, Tender tender, User user) {
        this.id = id;
        this.appliedAt = appliedAt;
        this.application = application;
        this.filePath = filePath;
        this.isArchived = isArchived;
        this.tender = tender;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Date appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.appliedAt);
        hash = 53 * hash + Objects.hashCode(this.application);
        hash = 53 * hash + Objects.hashCode(this.filePath);
        hash = 53 * hash + Objects.hashCode(this.isArchived);
        hash = 53 * hash + Objects.hashCode(this.tender);
        hash = 53 * hash + Objects.hashCode(this.user);
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
        final Proposal other = (Proposal) obj;
        if (!Objects.equals(this.application, other.application)) {
            return false;
        }
        if (!Objects.equals(this.filePath, other.filePath)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.appliedAt, other.appliedAt)) {
            return false;
        }
        if (!Objects.equals(this.isArchived, other.isArchived)) {
            return false;
        }
        if (!Objects.equals(this.tender, other.tender)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proposal{" + "id=" + id + ", appliedAt=" + appliedAt + ", application=" + application + ", filePath=" + filePath + ", isArchieved=" + isArchived + ", tender=" + tender + ", user=" + user + '}';
    }
}
