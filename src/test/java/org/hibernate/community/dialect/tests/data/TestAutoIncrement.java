package org.hibernate.community.dialect.tests.data;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(schema = "vip", name = "SPARK_TEST_AUTO_INCREMENT")
public class TestAutoIncrement {

    @Id
    // this trigger select max () from table
    //@GeneratedValue(generator="increment")
    //@GenericGenerator(name="increment", strategy = "increment")
    // testing to call identityColumn:
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;



    public TestAutoIncrement(String name, int age){
        this.name = name;
        this.age = age;
    }

    public TestAutoIncrement() {

    }

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
