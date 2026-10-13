package cl.eventpass.orders.queue;
import com.fasterxml.jackson.databind.ObjectMapper; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import software.amazon.awssdk.services.sqs.SqsClient; import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
@Component public class OrderPublisher {
 private final SqsClient sqs; private final ObjectMapper mapper=new ObjectMapper(); @Value("${sqs.queue-url}") private String queueUrl;
 public OrderPublisher(SqsClient sqs){this.sqs=sqs;}
 public void publish(OrderMessage m){try{sqs.sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(mapper.writeValueAsString(m)).build());}catch(Exception e){throw new IllegalStateException("No se pudo encolar la orden",e);}}
 public record OrderMessage(Long orderId,Long eventId,Long buyerId,int quantity){}
}
