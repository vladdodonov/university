package com.dodonov.university.domain;

import com.dodonov.university.domain.base.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "professor")
@Data
@EqualsAndHashCode(callSuper = true)
public class Professor extends Person {
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Course> courses;
}
