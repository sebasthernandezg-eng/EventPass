package cl.eventpass.processor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderWorkerTest {
    @Test
    void orderMessageCarriesIdentifiersAndQuantity() {
        OrderWorker.OrderMessage message = new OrderWorker.OrderMessage(12L, 4L, 8L, 2);
        assertEquals(12L, message.orderId());
        assertEquals(4L, message.eventId());
        assertEquals(8L, message.buyerId());
        assertEquals(2, message.quantity());
    }
}
