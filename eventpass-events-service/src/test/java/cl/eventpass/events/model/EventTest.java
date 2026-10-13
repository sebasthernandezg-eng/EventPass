package cl.eventpass.events.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void initializesAvailableCapacityAndUpdatesEventData() {
        Event event = new Event("Concierto", "Auditorio", LocalDateTime.of(2026, 12, 10, 20, 0), 10);
        assertEquals(10, event.getAvailable());
        event.update("Concierto actualizado", "Auditorio central", LocalDateTime.of(2026, 12, 11, 20, 0), 12);
        assertEquals("Concierto actualizado", event.getName());
        assertEquals(12, event.getCapacity());
        assertEquals(12, event.getAvailable());
    }

    @Test
    void rejectsCapacityBelowAlreadyReservedSeats() {
        Event event = new Event("Concierto", "Auditorio", LocalDateTime.of(2026, 12, 10, 20, 0), 10);
        assertThrows(IllegalArgumentException.class,
                () -> event.update("Concierto", "Auditorio", LocalDateTime.of(2026, 12, 10, 20, 0), 0));
    }
}
