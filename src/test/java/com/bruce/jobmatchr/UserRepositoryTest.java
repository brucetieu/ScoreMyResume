package com.bruce.jobmatchr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Test the UserRepository
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false) // Don't rollback transaction, all operations are commited to db
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;  // use for testing

    @Autowired
    private EntityManager entityManager;  // Find entities by their primary key

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("testemail@email.com");
        user.setPassword("password");
        user.setFirstName("test");
        user.setLastName("user");

        // save method defined by CrudRepositoryInterface; save this user to the database
        User savedUser = userRepository.save(user);

        // Find the user by id
        User existUser = entityManager.find(User.class, savedUser.getId());

        // Check that the user email which was saved to the database equals the user email we created.
        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "testemail@email.com";

        User savedUser = userRepository.findByEmail(email);
        assertThat(savedUser).isNotNull();
    }
}
