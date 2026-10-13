package cl.eventpass.auth.controller;
import cl.eventpass.auth.model.UserAccount; import cl.eventpass.auth.repository.UserRepository; import cl.eventpass.auth.security.JwtToken;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.security.SecureRandom; import javax.crypto.SecretKeyFactory; import javax.crypto.spec.PBEKeySpec; import java.util.*;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserRepository users; public AuthController(UserRepository users){this.users=users;}
 public record RegisterRequest(String username,String password,String role){}
 public record LoginRequest(String username,String password){}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> register(@RequestBody RegisterRequest r){
  if(r.username()==null||r.username().isBlank()||r.password()==null||r.password().length()<8) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario requerido y clave de mínimo 8 caracteres");
  String role=r.role()==null?"BUYER":r.role().toUpperCase(Locale.ROOT); if(!Set.of("BUYER","STAFF").contains(role)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Rol debe ser BUYER o STAFF");
  if(users.findByUsername(r.username()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Usuario ya existe");
  UserAccount u=users.save(new UserAccount(r.username(),hash(r.password()),role)); return Map.of("id",u.getId(),"username",u.getUsername(),"role",u.getRole());
 }
 @PostMapping("/login") public Map<String,Object> login(@RequestBody LoginRequest r){
  UserAccount u=users.findByUsername(r.username()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales inválidas"));
  if(!verify(r.password(),u.getPasswordHash())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales inválidas");
  return Map.of("tokenType","Bearer","token",JwtToken.issue(u.getId(),u.getRole()),"userId",u.getId(),"role",u.getRole());
 }
 private String hash(String password){try{byte[] salt=new byte[16];new SecureRandom().nextBytes(salt);byte[] out=derive(password,salt);return Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(out);}catch(Exception e){throw new IllegalStateException(e);}}
 private boolean verify(String p,String stored){try{String[] x=stored.split(":");return java.security.MessageDigest.isEqual(Base64.getDecoder().decode(x[1]),derive(p,Base64.getDecoder().decode(x[0])));}catch(Exception e){return false;}}
 private byte[] derive(String p,byte[] salt)throws Exception{PBEKeySpec spec=new PBEKeySpec(p.toCharArray(),salt,120000,256);try{return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}finally{spec.clearPassword();}}
}
