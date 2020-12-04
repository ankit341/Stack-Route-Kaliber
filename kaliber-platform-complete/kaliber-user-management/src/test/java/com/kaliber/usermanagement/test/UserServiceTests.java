package com.kaliber.usermanagement.test;

import com.kaliber.usermanagement.exception.UserNotFoundException;
import com.kaliber.usermanagement.model.User;
import com.kaliber.usermanagement.repository.UserRepository;
import com.kaliber.usermanagement.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsersTest() throws UserNotFoundException {

        ArrayList<User> userArrayList = new ArrayList<User>();

        User user = new User("Jhon@gmail.com","Jhon","jhon@gmail.com",50);
            userArrayList.add(user);
            when(userRepository.findAll()).thenReturn(userArrayList);

            ArrayList<User> userArrayListTest = (ArrayList<User>)userRepository.findAll();
            assertEquals(1,userArrayListTest.size());
            assertEquals("Jhon@gmail.com",userArrayListTest.get(0).getUsername());
            assertEquals("Jhon",userArrayListTest.get(0).getName());

    }

    @AfterEach
    public void deleteDocument(){
        userRepository.deleteAll();
    }

    @Test
    public void userNameTest() throws UserNotFoundException{

        ArrayList<User> userArrayList = new ArrayList<User>();

        User user = new User("Jhon@gmail.com","Jhon","jhon@gmail.com",50);
        userArrayList.add(user);
        when(userRepository.findAll()).thenReturn(userArrayList);

        ArrayList<User> userArrayListTest = (ArrayList<User>)userRepository.findAll();
        assertEquals("Jhon@gmail.com",userArrayListTest.get(0).getUsername());
    }
}
