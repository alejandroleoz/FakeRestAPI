package dds.db;

import dds.model.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PersonaRepository {

    public static final String PERSISTENCE_UNIT = "FakeRestAPI_PU";

    private EntityManager entityManager = null;

    public PersonaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void saveOrUpdate(Persona persona) {
        doInTx(() -> {
            if (find(persona.getDni()) != null) {
                entityManager.merge(persona);
            } else {
                entityManager.persist(persona);
            }
        });
    }

    public Persona find(Long id) {
        return entityManager.find(Persona.class, id);
    }

    public List<Persona> getAll() {
        String queryStr = "select p from Persona p";
        Query query = entityManager.createQuery(queryStr);
        return query.getResultList();
    }

    public boolean delete(Long dni) {
        Persona persona = entityManager.find(Persona.class, dni);
        if (persona == null) {
            return false;
        }
        doInTx(() -> {
            entityManager.remove(persona);
        });
        return true;
    }

    private void doInTx(TxUnit unit) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        unit.doInTx();
        tx.commit();
    }
}
