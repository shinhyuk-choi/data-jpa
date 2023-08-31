package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
//@Rollback(false)
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

    @Test
    void testParameterBinding() {
        // given
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName2", 10);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findByNames(Arrays.asList("userName1", "userName2"));

        // then
        assertThat(result.get(0).getUserName()).isEqualTo(member1.getUserName());
        assertThat(result.get(1).getUserName()).isEqualTo(member2.getUserName());

    }

    @Test
    void testReturnType() {
        // given
        Member member1 = new Member("userName1", 10);
        Member member2 = new Member("userName2", 10);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findListByUserName("userName1");
        Member result2 = memberRepository.findMemberByUserName("userName1");
        Member result4 = memberRepository.findMemberByUserName("userName4");
        Optional<Member> result3 = memberRepository.findOptionalByUserName("userName3");

        // then
        assertThat(result instanceof List<Member>).isEqualTo(true);
        assertThat(result2 instanceof Member).isEqualTo(true);
        assertThat(result3 instanceof Optional).isEqualTo(true);
        assertThat(result4).isEqualTo(null);


    }

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "userName"));

        // when
        Page<Member> result = memberRepository.findByAge(10, pageRequest);
        Page<MemberDto> toMap = result.map(member -> new MemberDto(member.getId(), member.getUserName(), null));

        // then
        List<Member> content = result.getContent();
        long totalElements = result.getTotalElements();
        assertThat(content.size()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(result.getNumber()).isEqualTo(1);
        assertThat(result.isFirst()).isFalse();
        assertThat(result.hasNext()).isTrue();

    }

}