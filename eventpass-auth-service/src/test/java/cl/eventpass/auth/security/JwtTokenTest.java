package cl.eventpass.auth.security;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenTest {
    @Test
    void issuesSignedJwtWithStaffRoleAndExpiration() {
        String token = JwtToken.issue(7L, "STAFF");
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        assertTrue(payload.contains("\"sub\":\"7\""));
        assertTrue(payload.contains("\"role\":\"STAFF\""));
        assertTrue(payload.contains("\"exp\":"));
    }
}
