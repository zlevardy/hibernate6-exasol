package org.hibernate.community.dialect.tests;

import java.util.List;
import jakarta.persistence.EntityManager;
import org.hibernate.community.dialect.tests.data.TestAutoIncrement;


public class AutoIncrementTest extends HibernateEntityManagerForTests {

    //private Session session;
    private EntityManager manager;

    public AutoIncrementTest() {
        this.manager = super.getManager();
    }

    public void testReadEntityId(){
        List<Long> res1 = manager.createQuery("SELECT id FROM TestAutoIncrement WHERE name='name1'").getResultList();
        TestAutoIncrement e1 = manager.find(TestAutoIncrement.class, res1.get(0));
        assertTrue(e1.getPid() == 1L);
    }

}