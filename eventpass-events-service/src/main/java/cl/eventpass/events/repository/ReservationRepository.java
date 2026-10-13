package cl.eventpass.events.repository;
import cl.eventpass.events.model.EventReservation; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface ReservationRepository extends JpaRepository<EventReservation,Long>{Optional<EventReservation> findByOrderId(Long orderId);}
