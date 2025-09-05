package org.hibernate.community.dialect.tests.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(schema = "vip", name = "SPARK_TEST_AUTO_INCREMENT")
public class TestAutoIncrement {


    private Long id;
    private String name;
    private int age;



    public TestAutoIncrement(String name, int age){
        this.name = name;
        this.age = age;
    }

    public TestAutoIncrement() {

    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Column(name="AGE")
    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }

}
