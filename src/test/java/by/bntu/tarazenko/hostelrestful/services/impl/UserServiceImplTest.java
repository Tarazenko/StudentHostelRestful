package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.Status;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.repository.RequestRepository;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private User user;
    private Request request;

    @Before
    public void init() {
        request = new Request();
        user = new User("testuser", "tes@mail", "pas", "testName", "surname", "patr");
        user.setId(1L);
        request.setId(1L);
        request.setUser(user);
        request.setStatus(Status.WAITING);
        request.setComment("Test comment");
    }

    @Test
    public void getByIdExpectUser() {
        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        User actualUser = userService.getById(1L);
        assertEquals(user, actualUser);
    }

    @Test
    @Ignore
    public void createUserExpectCreatedUser() {
        doReturn(user).when(userRepository).save(user);
        doReturn(Optional.of(user)).when(userRepository).findById(1l);
        User createdUser = userService.create(user);
        assertEquals(user, createdUser);
    }

    @Test
    public void getByIdExpectNotFoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("User with id 2 not found");
        doReturn(Optional.empty()).when(userRepository).findById(2L);
        User actualuser = userService.getById(2L);
        verify(userRepository, times(0)).findById(2L);
    }

    @Test
    public void deleteByIdExpectDeletion() {
        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        userService.deleteById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void deleteByIdExpectNotfoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("User with id 2 not found");
        doReturn(Optional.empty()).when(userRepository).findById(2L);
        userService.deleteById(2L);
        verify(userRepository, times(0)).deleteById(1L);
    }

}


