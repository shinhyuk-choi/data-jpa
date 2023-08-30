package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //    구현체가 없지만 동작을 하는 이유
//    spring data jpa가 구현체를 알아서 주입한다.
    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

    @Query("select m from Member  m where m.userName = :userName and m.age = :age")
        // 어플리케이션 로딩 시점에 파싱하기 때문에 오타 검증가능
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUserNameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.userName, t.teamName) from Member  m join m.team t")
    List<MemberDto> findMemberDto();
}
