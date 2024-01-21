package com.shashank.customer;

import com.shashank.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();


    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                new JdbcTemplate(getDataSource()),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);

        underTest.insertCustomer(customer);

//        When
        List<Customer> actualCustomer = underTest.selectAllCustomers();
//        Then
        assertThat(actualCustomer).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
//        When

        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);
//        Then
        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());

        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
//        Given
        int id = -1;
//        When
        var actualCustomer = underTest.selectCustomerById(id);
//        Then

        assertThat(actualCustomer).isEmpty();
    }

    @Test
    void insertCustomer() {
//        Given
//        When
//        Then

    }

    @Test
    void existsPersonWithEmail() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);
//     When
        boolean actualCustomer = underTest.existsPersonWithEmail(email);

//        Then
        assertThat(actualCustomer).isTrue();

    }


    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

//        When
        boolean actualCustomer = underTest.existsPersonWithEmail(email);
//        Then
        assertThat(actualCustomer).isFalse();
    }

    @Test
    void existsPersonWithId() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
//        When
        var actualCustomer = underTest.existsPersonWithId(id);
//        Then

        assertThat(actualCustomer).isTrue();
    }

    void existsPersonWithIdWillReturnFalseWhenIdNotPresent() {
//        Given
        int id = -1;
//        When
        var actualCustomer = underTest.existsPersonWithId(id);
//        Then
        assertThat(actualCustomer).isFalse();
    }

    @Test
    void deleteCustomerById() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);

        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

//        When
        underTest.deleteCustomerById(id);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);
        assertThat(actualCustomer).isNotPresent();

    }

    @Test
    void updateCustomerName() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);

        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";

//        When name is changed
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(newName);

        underTest.updateCustomer(updatedCustomer);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);


        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerEmail() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

//         When email is changed
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setEmail(newEmail);
        underTest.updateCustomer(updatedCustomer);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);

        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }


    @Test
    void updateCustomerAge() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newAge = 100;

//         When age is changed
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setAge(newAge);

        underTest.updateCustomer(updatedCustomer);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);

        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(newAge);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });

    }


    @Test
    void willUpdateAllPropertiesCustomer() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


//         When everything is updated
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName("foo");
        String newEmail = UUID.randomUUID().toString();
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setAge(22);

        underTest.updateCustomer(updatedCustomer);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);

        assertThat(actualCustomer).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getId()).isEqualTo(id);
            assertThat(updated.getGender()).isEqualTo(Gender.MALE);
            assertThat(updated.getName()).isEqualTo("foo");
            assertThat(updated.getEmail()).isEqualTo(newEmail);
            assertThat(updated.getAge()).isEqualTo(22);
        });

    }


    @Test
    void willNotUpdateWhenNothingToUpdate() {
//        Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


//         When update without no changes
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);


        underTest.updateCustomer(updatedCustomer);


//        Then
        Optional<Customer> actualCustomer = underTest.selectCustomerById(id);

        assertThat(actualCustomer).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });

    }
}