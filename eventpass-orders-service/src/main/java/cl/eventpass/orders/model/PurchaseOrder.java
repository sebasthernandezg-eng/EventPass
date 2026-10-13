package cl.eventpass.orders.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="orders") public class PurchaseOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long eventId;
 @Column(nullable=false) private Long buyerId;
 @Column(nullable=false) private int quantity;
 @Column(nullable=false,length=24) private String status;
 @Column(nullable=false) private Instant createdAt;
 protected PurchaseOrder(){}
 public PurchaseOrder(Long eventId,Long buyerId,int quantity){this.eventId=eventId;this.buyerId=buyerId;this.quantity=quantity;this.status="ACEPTADA_PENDIENTE";this.createdAt=Instant.now();}
 public Long getId(){return id;} public Long getEventId(){return eventId;} public Long getBuyerId(){return buyerId;} public int getQuantity(){return quantity;} public String getStatus(){return status;} public Instant getCreatedAt(){return createdAt;} public void setStatus(String s){status=s;}
}
