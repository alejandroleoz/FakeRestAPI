package dds;

import dds.db.EntityManagerFactory;
import dds.db.PersonaRepository;
import dds.db.TxUnit;
import dds.model.Persona;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class RepositoryTest {

    private PersonaRepository personaRepository;
    private EntityManager entityManager;

    @Before
    public void before() {
        this.entityManager = EntityManagerFactory.getInstance().getEntityManager();
        this.personaRepository = new PersonaRepository(this.entityManager);
    }

    
    @Test
    public void saveOrUpdate() {
        Persona persona = new Persona(123L);
        persona.setNombre("Alejandro");
        personaRepository.saveOrUpdate(persona);

        persona = entityManager.find(Persona.class, 123L);
        assertThat(persona.getNombre()).isEqualTo("Alejandro");

        persona.setNombre("Ezequiel");
        personaRepository.saveOrUpdate(persona);

        persona = entityManager.find(Persona.class, 123L);
        assertThat(persona.getNombre()).isEqualTo("Ezequiel");
    }

    @Test
    public void find() {
        Persona persona = new Persona(123L);
        persona.setNombre("Alejandro");
        executeInDBTx(() -> {
            entityManager.persist(persona);
        });

        Persona personaFromDB = personaRepository.find(11L);
        assertThat(personaFromDB).isNull();

        personaFromDB = personaRepository.find(123L);
        assertThat(personaFromDB).isNotNull();
        assertThat(personaFromDB).isEqualTo(persona);
    }

    @Test
    public void delete() {
        executeInDBTx(() -> {
            Persona persona = new Persona(123L);
            persona.setNombre("Rosa");
            entityManager.persist(persona);
        });
        assertThat(getAllFromDB()).hasSize(1);

        boolean deleteResult = personaRepository.delete(111L);
        assertThat(deleteResult).isFalse();
        assertThat(getAllFromDB()).hasSize(1);

        deleteResult = personaRepository.delete(123L);
        assertThat(deleteResult).isTrue();
        assertThat(getAllFromDB()).isEmpty();
    }

    @Test
    public void getAll() {
        executeInDBTx(() -> {
            Persona persona = new Persona(999L);
            persona.setNombre("Rosa");
            entityManager.persist(persona);

            persona = new Persona(888L);
            persona.setNombre("Maria");
            entityManager.persist(persona);
        });


        List<Persona> personas = personaRepository.getAll();
        assertThat(personas).hasSize(2);
        assertThat(personas)
                .extracting(Persona::getDni)
                .containsExactlyInAnyOrder(999L, 888L);
    }

    private void executeInDBTx(TxUnit unit) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        unit.doInTx();
        tx.commit();
    }

    private List<Persona> getAllFromDB() {
        return entityManager.createQuery("select p from Persona p").getResultList();
    }
}
