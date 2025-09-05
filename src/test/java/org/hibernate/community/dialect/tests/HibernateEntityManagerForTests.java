package org.hibernate.community.dialect.tests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;
import org.hibernate.community.dialect.tests.data.TestAutoIncrement;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

abstract class HibernateEntityManagerForTests extends TestCase {

    private static EntityManagerFactory sessionFactory;

    private static EntityManagerFactory buildSessionFactory() {
        try {
            sessionFactory = Persistence.createEntityManagerFactory("org.hibernate.exasol.jpa");
            return sessionFactory;
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }


    @Override
    /**
     * create dataset in EXASOL via Hibernate
     */
    protected void setUp(){
        List<TestAutoIncrement> data = new LinkedList<>();
        data.add(new TestAutoIncrement("name1", 1));
        data.add(new TestAutoIncrement("name2", 2));
        data.add(new TestAutoIncrement("name3", 3));

        EntityManager manager = getSessionFactory().createEntityManager();
        manager.getTransaction().begin();
        ListIterator<TestAutoIncrement> it = data.listIterator();
        while(it.hasNext()){
            manager.persist(it.next());
        }
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    /**
     * Do nothing, data is erased at next startup
     */
    protected void tearDown(){
        EntityManager manager = getSessionFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.createQuery("delete from TestAutoIncrement").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    protected EntityManager getManager(){
        return getSessionFactory().createEntityManager();
    }



}