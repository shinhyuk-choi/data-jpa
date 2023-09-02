package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Item implements Persistable<String> {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;
    public Item(String id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
