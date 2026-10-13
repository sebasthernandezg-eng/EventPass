package cl.eventpass.orders.repository; import cl.eventpass.orders.model.PurchaseOrder; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface OrderRepository extends JpaRepository<PurchaseOrder,Long>{List<PurchaseOrder> findByBuyerId(Long buyerId);}
