package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    private SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        //Session 생성
        Member member = new Member();
        this.sessionManager.createSession(member, response);


        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // Session 조회
        Object result = this.sessionManager.getSession(request);

        Assertions.assertThat(result).isEqualTo(member);

        //Session Expire
        this.sessionManager.expire(request);
        Object expired = this.sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }

}
