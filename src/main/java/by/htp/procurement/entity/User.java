package by.htp.procurement.entity;

import java.util.List;
import java.util.Objects;

public class User  extends Entity implements Comparable<User> {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isAdmin;
    private Boolean isArchieved;

    /*ManyToOne*/
    private Company company;
    
    /*OneToMany*/
    private List<Tender> tenders;
    /*OneToMany*/
    private List<Proposal> proposals;
    

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String firstName, String lastName, String email, Boolean isAdmin, Boolean isArchieved, Company company) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isArchieved = isArchieved;
        this.company = company;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsArchieved() {
        return isArchieved;
    }

    public void setIsArchieved(Boolean isArchieved) {
        this.isArchieved = isArchieved;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Tender> getTenders() {
        return tenders;
    }

    public void setTenders(List<Tender> tenders) {
        this.tenders = tenders;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.login);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.firstName);
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.isAdmin);
        hash = 67 * hash + Objects.hashCode(this.isArchieved);
        hash = 67 * hash + Objects.hashCode(this.company);
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
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.isAdmin, other.isAdmin)) {
            return false;
        }
        if (!Objects.equals(this.isArchieved, other.isArchieved)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", isAdmin=" + isAdmin + ", isArchieved=" + isArchieved + ", company=" + company + '}';
    }

    @Override
    public int compareTo(User user) {
        return user.getLogin().compareTo(login);
    }
}
