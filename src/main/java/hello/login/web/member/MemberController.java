package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping(value = "/add")
    public String addForm(@ModelAttribute(name = "member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping(value = "/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "members/addMemberForm";
        this.memberRepository.save(member);
        return "redirect:/";
    }

}
