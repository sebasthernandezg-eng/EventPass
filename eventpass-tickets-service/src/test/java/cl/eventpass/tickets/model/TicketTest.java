package cl.eventpass.tickets.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    @Test
    void generatesUniqueCodeAndKeepsOrderReferences() {
        Ticket first = new Ticket(4L, 2L, 8L);
        Ticket second = new Ticket(4L, 2L, 8L);
        assertNotNull(first.getAccessCode());
        assertNotEquals(first.getAccessCode(), second.getAccessCode());
        assertEquals(4L, first.getOrderId());
        assertEquals(2L, first.getEventId());
        assertEquals(8L, first.getBuyerId());
        assertFalse(first.isUsed());
    }
}
