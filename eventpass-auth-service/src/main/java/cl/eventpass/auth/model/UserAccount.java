package cl.eventpass.auth.model;
import jakarta.persistence.*;
@Entity @Table(name="users") public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String username;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false,length=20) private String role;
 protected UserAccount(){}
 public UserAccount(String username,String passwordHash,String role){this.username=username;this.passwordHash=passwordHash;this.role=role;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getPasswordHash(){return passwordHash;} public String getRole(){return role;}
}
