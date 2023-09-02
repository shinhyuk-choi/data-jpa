package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void save() {
        // given
        Item item = new Item();

        // when
        itemRepository.save(item);
        // SimpleJpaRepository 에서 id 가 null 이면 persist 호출 후, entity에 id 값이 생성된다.
        // merge를 타게 되면 select 후 update 또는 insert가 실행된다.

        // then
    }


}