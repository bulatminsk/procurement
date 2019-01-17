package by.htp.procurement.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Tender extends Entity {

    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer price;
    private Date deadlineAt;
    private Date publishedAt;
    private Boolean isArchived;
    /*Many to one - user owner*/
    private User user;
    /*Many to one - company winned tender*/
    private Company winner;
    /*One to many*/
    private Set<Evaluation> criteria;

    public Tender() {
    }

    public Tender(Integer id, String name, String category, String description, Integer price, Date deadlineAt, Date publishedAt, Boolean isArchived, User user, Company winner) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.deadlineAt = deadlineAt;
        this.publishedAt = publishedAt;
        this.isArchived = isArchived;
        this.user = user;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDeadlineAt() {
        return deadlineAt;
    }

    public void setDeadlineAt(Date deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getWinner() {
        return winner;
    }

    public void setWinner(Company winner) {
        this.winner = winner;
    }

    public Set<Evaluation> getCriteria() {
        return criteria;
    }

    public void setCriteria(Set<Evaluation> criteria) {
        this.criteria = criteria;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.category);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.price);
        hash = 53 * hash + Objects.hashCode(this.deadlineAt);
        hash = 53 * hash + Objects.hashCode(this.publishedAt);
        hash = 53 * hash + Objects.hashCode(this.isArchived);
        hash = 53 * hash + Objects.hashCode(this.user);
        hash = 53 * hash + Objects.hashCode(this.winner);
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
        final Tender other = (Tender) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.deadlineAt, other.deadlineAt)) {
            return false;
        }
        if (!Objects.equals(this.publishedAt, other.publishedAt)) {
            return false;
        }
        if (!Objects.equals(this.isArchived, other.isArchived)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.winner, other.winner)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tender{" + "id=" + id + ", name=" + name + ", category=" + category + ", description=" + description + ", price=" + price + ", deadlineAt=" + deadlineAt + ", publishedAt=" + publishedAt + ", isArchived=" + isArchived + ", user=" + user + ", winner=" + winner + '}';
    }
}
