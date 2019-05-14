package dds.rest;

import dds.db.EntityManagerFactory;
import dds.db.PersonaRepository;
import dds.model.Persona;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class PersonaController {

    private PersonaRepository personaRepository = new PersonaRepository(EntityManagerFactory.getInstance().getEntityManager());

    @RequestMapping(method = RequestMethod.GET, path = "/Persona/")
    public List<Persona> getAll() {
        return personaRepository.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/Persona/")
    public void save(@RequestBody Persona persona) {
        personaRepository.saveOrUpdate(persona);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/Persona/{dni}")
    public Persona get(@PathVariable Long dni) {
        return personaRepository.find(dni);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/Persona/{dni}")
    public void delete(@PathVariable Long dni) {
        if (!personaRepository.delete(dni)) {
            String msg = String.format("No  se pudo borrar Persona con DNI=%d", dni);
            throw new RuntimeException(msg);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/Persona/")
    public void update(@RequestBody Persona persona) {
        Persona persistedPersona = personaRepository.find(persona.getDni());
        if (persistedPersona == null) {
            throw new RuntimeException("La persona no existe");
        }
        personaRepository.saveOrUpdate(persona);
    }
}
