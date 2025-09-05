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

    /*
    public void testReadEntityId(){
        List<Long> res1 = manager.createQuery("SELECT id FROM TestAutoIncrement WHERE name='name1'").getResultList();
        TestAutoIncrement e1 = manager.find(TestAutoIncrement.class, res1.get(0));
        assertTrue(e1.getId() == 1L);
    }
    */

    public void testInsert() {
        List<Long> resMax = manager.createQuery("SELECT MAX(id) FROM TestAutoIncrement").getResultList();
        TestAutoIncrement max = manager.find(TestAutoIncrement.class, resMax.get(0));
        long maxId = (long) max.getId();
        long nextId = maxId + 1;

        manager.getTransaction().begin();
        TestAutoIncrement nextItem = new TestAutoIncrement("name"+nextId, 4);
        manager.getTransaction();
        manager.persist( nextItem );
        manager.getTransaction().commit();

        List<Long> res = manager.createQuery("SELECT id FROM TestAutoIncrement WHERE name='name"+nextId+"'").getResultList();
        TestAutoIncrement item = manager.find(TestAutoIncrement.class, res.get(0));
        assertEquals(nextId, (long) item.getId());
    }

}