package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(findMember.getUserName()).isEqualTo(memberA.getUserName());
        assertThat(findMember).isEqualTo(memberA);
        // equals hash 를 override 하지 않았음에도 같다라는 결과가 나오는 이유
        // JPA는 영속성 컨텍스트의 동일성을 보장을 한다. | 1차 캐시
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName1", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("userName1", 15);

        assertThat(result.get(0).getUserName()).isEqualTo("userName1");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void paging() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        // when
        List<Member> result = memberJpaRepository.findByPage(10, 1, 4);
        Long totalCount = memberJpaRepository.totalCount(10);

        // then
        assertThat(result.get(0).getUserName()).isEqualTo("member4");
        assertThat(result.get(1).getUserName()).isEqualTo("member3");
        assertThat(result.get(2).getUserName()).isEqualTo("member2");
        assertThat(result.get(3).getUserName()).isEqualTo("member1");
        assertThat(totalCount).isEqualTo(5);


    }

    @Test
    void bulkUpdate() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 22));

        // when
        int resultCount = memberJpaRepository.bulkAgePlus(20);

        // then
        assertThat(resultCount).isEqualTo(3);
    }

}