package com.example.magnus;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.magnus.IsActiveUser.isActiveUser;
import static com.example.magnus.IsEven.isEven;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestTest {

    @Test
    public void testEvenNumber(){
        assertThat(4, isEven());
    }

    @Test
    public void testActiveUser(){
        assertThat(new User("Ali", true), isActiveUser());
    }

    @Test
    public void testNamesList(){
        List<String> names = List.of("Alice", "Bob", "Jason", "Clara");

        assertThat(names, hasSize(4));

        assertThat(names, everyItem(matchesPattern("^[A-Z].*")));

        assertThat(names, hasItem(containsString("son")));
    }
}
