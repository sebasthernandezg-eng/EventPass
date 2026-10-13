package cl.eventpass.orders.queue;
import org.springframework.context.annotation.*; import software.amazon.awssdk.auth.credentials.*; import software.amazon.awssdk.regions.Region; import software.amazon.awssdk.services.sqs.SqsClient; import java.net.URI;
@Configuration public class SqsConfig {
 @Bean SqsClient sqsClient(){return SqsClient.builder().endpointOverride(URI.create(System.getenv().getOrDefault("SQS_ENDPOINT","http://localhost:4566"))).region(Region.of(System.getenv().getOrDefault("AWS_REGION","us-east-1"))).credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(System.getenv().getOrDefault("AWS_ACCESS_KEY_ID","test"),System.getenv().getOrDefault("AWS_SECRET_ACCESS_KEY","test")))).build();}
}
