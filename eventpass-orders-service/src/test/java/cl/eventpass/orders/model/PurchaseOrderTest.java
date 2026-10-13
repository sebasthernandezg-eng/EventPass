package cl.eventpass.orders.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseOrderTest {
    @Test
    void newOrderStartsAcceptedAndPending() {
        PurchaseOrder order = new PurchaseOrder(3L, 8L, 2);
        assertEquals(3L, order.getEventId());
        assertEquals(8L, order.getBuyerId());
        assertEquals(2, order.getQuantity());
        assertEquals("ACEPTADA_PENDIENTE", order.getStatus());
        assertNotNull(order.getCreatedAt());
    }
}
