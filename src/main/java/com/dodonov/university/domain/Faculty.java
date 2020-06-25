package com.dodonov.university.domain;

import com.dodonov.university.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "faculty")
@Data
@EqualsAndHashCode(callSuper = true)
public class Faculty extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Course> courses;
}
