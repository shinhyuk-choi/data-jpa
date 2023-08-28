package study.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }
}