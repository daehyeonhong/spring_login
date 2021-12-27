package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;

    //    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    //    @GetMapping(value = "/")
    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) return "home";

        Member loginMember = this.memberRepository.findById(memberId);
        if (loginMember == null) return "home";
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //    @GetMapping(value = "/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
// SessionManager에 저장된 회원 정보 조회
        Member member = (Member) this.sessionManager.getSession(request);
        if (member == null) return "home";
        model.addAttribute("member", member);
        return "loginHome";
    }

    //    @GetMapping(value = "/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
// SessionManager에 저장된 회원 정보 조회
        HttpSession session = request.getSession(false);
        if (session == null) return "home";

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) return null;
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping(value = "/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
// SessionManager에 저장된 회원 정보 조회
        if (member == null) return null;
        model.addAttribute("member", member);
        return "loginHome";
    }

}
