package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testMember() {
        // given
        Member memberA = new Member("memberA");

        // when
        Member savedMember = memberRepository.save(memberA);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getUserName()).isEqualTo(memberA.getUserName());
        assertThat(findMember).isEqualTo(memberA);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName1", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUserNameAndAgeGreaterThan("userName1", 15);

        assertThat(result.get(0).getUserName()).isEqualTo("userName1");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testQuery() {
        // given
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName1", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findUser("userName1", 20);

        // then
        assertThat(result.get(0).getUserName()).isEqualTo(member2.getUserName());
        assertThat(result.get(0).getAge()).isEqualTo(member2.getAge());
    }

    @Test
    void testQuery2() {
        // given
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName1", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<String> result = memberRepository.findUserNameList();

        // then
        assertThat(result.get(0)).isEqualTo(member2.getUserName());
        assertThat(result.get(0)).isEqualTo(member2.getUserName());
    }

    @Test
    void testQuery3() {
        // given
        Member member1 = new Member("userName1", 10);
        Team team1 = new Team("teamName");
        member1.changeTeam(team1);
        teamRepository.save(team1);
        memberRepository.save(member1);

        // when
        List<MemberDto> result = memberRepository.findMemberDto();

        // then
        assertThat(result.get(0).getTeamName()).isEqualTo(team1.getTeamName());
        assertThat(result.get(0).getUserName()).isEqualTo(member1.getUserName());

    }

}