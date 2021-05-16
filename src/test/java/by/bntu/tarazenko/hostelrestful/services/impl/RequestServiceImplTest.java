package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.*;
import by.bntu.tarazenko.hostelrestful.repository.RequestRepository;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class RequestServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private User user;
    private Request request;

    @Before
    public void init(){
        request = new Request();
        user = new User("testuser", "tes@mail", "pas", "testName", "surname", "patr");
        user.setId(1L);
        request.setId(1L);
        request.setUser(user);
        request.setStatus(Status.WAITING);
        request.setComment("Test comment");
    }

    @Test
    public void getByIdExpectRequest() {
        doReturn(Optional.of(request)).when(requestRepository).findById(1L);
        Request actualRequest = requestService.getById(1L);
        assertEquals(request, actualRequest);
    }

    @Test
    public void createRequestExpectCreatedRequest() {
        doReturn(request).when(requestRepository).save(request);
        doReturn(Optional.of(user)).when(userRepository).findById(1l);
        Request createdRequest = requestService.create(request);
        assertEquals(request, createdRequest);
    }

    @Test
    public void getByIdExpectNotFoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Request with id 2 not found");
        doReturn(Optional.empty()).when(requestRepository).findById(2L);
        Request actualRequest = requestService.getById(2L);
        verify(requestRepository, times(0)).findById(2L);
    }

    @Test
    public void deleteByIdExpectDeletion() {
        doReturn(Optional.of(request)).when(requestRepository).findById(1L);
        requestService.deleteById(1L);
        verify(requestRepository).deleteById(1L);
    }

    @Test
    public void deleteByIdExpectNotfoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Request with id 2 not found");
        doReturn(Optional.empty()).when(requestRepository).findById(2L);
        requestService.deleteById(2L);
        verify(requestRepository, times(0)).deleteById(1L);
    }

}
