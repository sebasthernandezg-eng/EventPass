package cl.eventpass.events.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDateTime;
@Entity @Table(name="events") public class Event {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false,length=140) private String name;
 @NotBlank @Column(nullable=false,length=180) private String location;
 @NotNull @Column(name="event_date",nullable=false) private LocalDateTime eventDate;
 @Min(1) @Column(nullable=false) private int capacity;
 @Column(nullable=false) private int available;
 protected Event(){}
 public Event(String name,String location,LocalDateTime eventDate,int capacity){this.name=name;this.location=location;this.eventDate=eventDate;this.capacity=capacity;this.available=capacity;}
 public Long getId(){return id;} public String getName(){return name;} public String getLocation(){return location;} public LocalDateTime getEventDate(){return eventDate;} public int getCapacity(){return capacity;} public int getAvailable(){return available;}
 public void update(String n,String l,LocalDateTime d,int c){int sold=capacity-available;if(c<1||c<sold)throw new IllegalArgumentException("El nuevo aforo debe ser positivo y no menor que entradas ya reservadas");name=n;location=l;eventDate=d;capacity=c;available=c-sold;}
}
