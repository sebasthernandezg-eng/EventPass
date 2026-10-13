package cl.eventpass.tickets.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="tickets",uniqueConstraints=@UniqueConstraint(name="uk_ticket_code",columnNames="access_code")) public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="access_code",nullable=false,unique=true,length=36) private String accessCode;
 @Column(nullable=false) private Long orderId; @Column(nullable=false) private Long eventId; @Column(nullable=false) private Long buyerId;
 @Column(nullable=false) private boolean used=false; @Column(nullable=false) private Instant issuedAt=Instant.now();
 protected Ticket(){} public Ticket(Long orderId,Long eventId,Long buyerId){this.accessCode=java.util.UUID.randomUUID().toString();this.orderId=orderId;this.eventId=eventId;this.buyerId=buyerId;}
 public Long getId(){return id;} public String getAccessCode(){return accessCode;} public Long getOrderId(){return orderId;} public Long getEventId(){return eventId;} public Long getBuyerId(){return buyerId;} public boolean isUsed(){return used;} public Instant getIssuedAt(){return issuedAt;}
}
