package com.lab;

import java.util.ArrayList;
import java.util.List;

/** In-memory store of student records. */
public class StudentStore {

    private final List<String> students = new ArrayList<>();

    /** Adds a student. Blank names are rejected. */
    public void add(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name is required");
        }
        students.add(name.trim());
    }

    /** Returns all students. */
    public List<String> list() {
        return List.copyOf(students);
    }

    /** Returns how many students are stored. */
    public int count() {
        return students.size();
    }
    /** Returns students whose name contains the given text. */
    public List<String> search(String text) {
        return students.stream()
                .filter(s -> s.toLowerCase().contains(text.toLowerCase()))
                .toList();
    }
}
