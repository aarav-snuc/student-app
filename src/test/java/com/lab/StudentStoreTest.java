package com.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentStoreTest {

    @Test
    void addsAndListsStudents() {
        StudentStore store = new StudentStore();
        store.add("Asha");
        store.add("Ravi");

        assertEquals(2, store.count());
        assertTrue(store.list().contains("Asha"));
        assertTrue(store.list().contains("Ravi"));
    }

    @Test
    void rejectsBlankName() {
        StudentStore store = new StudentStore();
        assertThrows(IllegalArgumentException.class, () -> store.add("   "));
    }
}
