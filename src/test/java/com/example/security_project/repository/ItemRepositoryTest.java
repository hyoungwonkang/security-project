package com.example.security_project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.security_project.domain.Item;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Rollback(false)
    void testSave() {
        List<Item> items = new ArrayList<>();

        items.add(Item.builder().name("냉장고").build());
        items.add(Item.builder().name("세탁기").build());
        items.add(Item.builder().name("TV").build());
        items.add(Item.builder().name("에어컨").build());
    
        List<Item> saved = itemRepository.saveAll(items);
    
        assertThat(saved).isNotEmpty();
    } 
}
