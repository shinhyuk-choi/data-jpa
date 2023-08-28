package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        Member memberA = new Member("memberA");
        Member savedMember = memberJpaRepository.save(memberA);
        Member findMember = memberJpaRepository.find(savedMember.getId());
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getName()).isEqualTo(memberA.getName());
        assertThat(findMember).isEqualTo(memberA);
        // equals hash 를 override 하지 않았음에도 같다라는 결과가 나오는 이유
        // JPA는 영속성 컨텍스트의 동일성을 보장을 한다. | 1차 캐시
    }

    @Test
    void save() {
    }

    @Test
    void find() {
    }
}