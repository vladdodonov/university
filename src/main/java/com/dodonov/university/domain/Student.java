package com.dodonov.university.domain;

import com.dodonov.university.domain.base.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "student")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<Course> courses;
}
