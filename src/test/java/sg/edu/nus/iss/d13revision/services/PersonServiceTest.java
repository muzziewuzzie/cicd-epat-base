package sg.edu.nus.iss.d13revision.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sg.edu.nus.iss.d13revision.models.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        // Initialize a fresh instance of PersonService before each test
        personService = new PersonService();
    }

    @Test
    void testInitialPersonsLoaded() {
        List<Person> persons = personService.getPersons();
        
        assertNotNull(persons, "Persons list should not be null");
        assertEquals(2, persons.size(), "Initial list should contain exactly 2 persons");
        
        assertEquals("Mark", persons.get(0).getFirstName());
        assertEquals("Zuckerberg", persons.get(0).getLastName());
        
        assertEquals("Elon", persons.get(1).getFirstName());
        assertEquals("Musk", persons.get(1).getLastName());
    }

    @Test
    void testAddPerson() {
        Person newPerson = new Person("Bill", "Gates");
        personService.addPerson(newPerson);

        List<Person> persons = personService.getPersons();
        assertEquals(3, persons.size(), "List size should increase to 3 after adding a person");
        
        Person addedPerson = persons.get(2);
        assertEquals("Bill", addedPerson.getFirstName());
        assertEquals("Gates", addedPerson.getLastName());
    }

    @Test
    void testUpdatePerson() {
        List<Person> persons = personService.getPersons();
        Person originalPerson = persons.get(0); // Mark Zuckerberg

        // Create an updated version keeping the same ID
        Person updatedInfo = new Person();
        updatedInfo.setId(originalPerson.getId());
        updatedInfo.setFirstName("Markus");
        updatedInfo.setLastName("Zuck");

        personService.updatePerson(updatedInfo);

        // Verify changes took effect
        Person found = personService.getPersons().stream()
                .filter(p -> p.getId().equals(originalPerson.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(found, "Updated person should still exist in the list");
        assertEquals("Markus", found.getFirstName(), "First name should be updated");
        assertEquals("Zuck", found.getLastName(), "Last name should be updated");
    }

    @Test
    void testRemovePerson() {
        List<Person> persons = personService.getPersons();
        Person personToRemove = persons.get(0); // Mark Zuckerberg

        personService.removePerson(personToRemove);

        List<Person> remainingPersons = personService.getPersons();
        assertEquals(1, remainingPersons.size(), "List size should decrease to 1 after removal");
        
        boolean stillExists = remainingPersons.stream()
                .anyMatch(p -> p.getId().equals(personToRemove.getId()));
        
        assertFalse(stillExists, "Removed person should no longer exist in the list");
    }
}