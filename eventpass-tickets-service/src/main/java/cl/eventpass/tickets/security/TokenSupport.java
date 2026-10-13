package cl.eventpass.tickets.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TokenSupport {
  private TokenSupport() {}
  private static String secret() { return System.getenv().getOrDefault("JWT_SECRET", "local-demo-secret-change-before-sharing-123456789"); }
  private static byte[] sign(String value) throws Exception {
    Mac mac=Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(secret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
  }
  public static Claims claims(HttpServletRequest request) {
    String header=request.getHeader("Authorization");
    if(header==null || !header.startsWith("Bearer ")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token Bearer requerido");
    try {
      String[] parts=header.substring(7).split("\\."); if(parts.length!=3) throw new Exception("formato");
      byte[] expected=sign(parts[0]+"."+parts[1]); byte[] provided=Base64.getUrlDecoder().decode(parts[2]);
      if(!MessageDigest.isEqual(expected,provided)) throw new Exception("firma");
      String json=new String(Base64.getUrlDecoder().decode(parts[1]),StandardCharsets.UTF_8);
      String sub=match(json,"\"sub\":\"([^\"]+)\"");
      String role=match(json,"\"role\":\"([^\"]+)\"");
      long exp=Long.parseLong(match(json,"\"exp\":([0-9]+)"));
      if(System.currentTimeMillis()/1000>exp) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token expirado");
      return new Claims(sub,role);
    } catch(ResponseStatusException e) { throw e; } catch(Exception e) { throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token inválido"); }
  }
  private static String match(String json,String regex) { Matcher m=Pattern.compile(regex).matcher(json); if(!m.find()) throw new IllegalArgumentException(); return m.group(1); }
  public static Claims require(HttpServletRequest req,String role) { Claims c=claims(req); if(!role.equals(c.role())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Rol insuficiente"); return c; }
  public record Claims(String subject,String role) {}
}
