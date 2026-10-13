package cl.eventpass.events.repository; import cl.eventpass.events.model.Event; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param;
public interface EventRepository extends JpaRepository<Event,Long>{
 @Modifying @Query("update Event e set e.available=e.available-:quantity where e.id=:id and e.available>=:quantity") int reserve(@Param("id") Long id,@Param("quantity") int quantity);
}
