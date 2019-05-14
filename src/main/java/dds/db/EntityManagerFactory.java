package dds.db;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static dds.db.PersonaRepository.PERSISTENCE_UNIT;

public class EntityManagerFactory {

    private static EntityManagerFactory instance = null;
    private final EntityManager entityManager;


    public static EntityManagerFactory getInstance() {
        if (instance == null) {
            instance = new EntityManagerFactory();
        }
        return instance;
    }

    public EntityManagerFactory() {
        javax.persistence.EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        entityManager = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
