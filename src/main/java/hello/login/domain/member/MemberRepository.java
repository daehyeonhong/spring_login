package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        this.store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return this.store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        /*List<Member> all = this.findAll();
        for (Member member : all)
            if (member.getLoginId().equals(loginId))
                return Optional.of(member);
        return Optional.empty();*/
        return this.findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(this.store.values());
    }

    public void clearStore() {
        this.store.clear();
    }

}
