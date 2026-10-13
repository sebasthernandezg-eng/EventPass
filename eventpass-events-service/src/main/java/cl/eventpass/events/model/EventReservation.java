package cl.eventpass.events.model;
import jakarta.persistence.*;
@Entity @Table(name="event_reservations",uniqueConstraints=@UniqueConstraint(name="uk_reservation_order",columnNames="order_id"))
public class EventReservation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="order_id",nullable=false,unique=true) private Long orderId;
 @Column(name="event_id",nullable=false) private Long eventId;
 @Column(nullable=false) private int quantity;
 protected EventReservation(){}
 public EventReservation(Long orderId,Long eventId,int quantity){this.orderId=orderId;this.eventId=eventId;this.quantity=quantity;}
 public Long getOrderId(){return orderId;} public Long getEventId(){return eventId;} public int getQuantity(){return quantity;}
}
