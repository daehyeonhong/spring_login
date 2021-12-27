package hello.login.web.session;

import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session 관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * Session 생성
     * * sessionId 생성 (임의의 추정 불가능한 RANDOM_VALUE->UUID)
     * * Session Store에 sessionId와 보관할 값 저장
     * * sessionId로 response cookie 생성해서 client에 전달
     */
    public void createSession(Object value, HttpServletResponse response) {
        // sessionId 생성하고, 값을 저장
        String sessionId = UUID.randomUUID().toString();

        this.sessionStore.put(sessionId, value);

        // Cookie 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * Session 조희
     * @param request
     * @return
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null)
            return null;
        return this.sessionStore.get(sessionCookie.getValue());
    }

    /**
     * Session expire
     * @param request
     */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null)
            this.sessionStore.remove(sessionCookie.getValue());
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}
