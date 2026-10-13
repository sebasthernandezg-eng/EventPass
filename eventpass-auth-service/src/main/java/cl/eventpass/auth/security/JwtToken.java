package cl.eventpass.auth.security;
import javax.crypto.Mac; import javax.crypto.spec.SecretKeySpec; import java.nio.charset.StandardCharsets; import java.util.Base64;
public final class JwtToken {
 private JwtToken(){}
 public static String issue(Long userId,String role){
  try { String header=enc("{\"alg\":\"HS256\",\"typ\":\"JWT\"}"); long exp=System.currentTimeMillis()/1000+7200;
   String payload=enc("{\"sub\":\""+userId+"\",\"role\":\""+role+"\",\"exp\":"+exp+"}"); String input=header+"."+payload;
   Mac mac=Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(secret().getBytes(StandardCharsets.UTF_8),"HmacSHA256"));
   return input+"."+Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(input.getBytes(StandardCharsets.UTF_8)));
  } catch(Exception e){throw new IllegalStateException(e);}
 }
 private static String secret(){return System.getenv().getOrDefault("JWT_SECRET","local-demo-secret-change-before-sharing-123456789");}
 private static String enc(String s){return Base64.getUrlEncoder().withoutPadding().encodeToString(s.getBytes(StandardCharsets.UTF_8));}
}
