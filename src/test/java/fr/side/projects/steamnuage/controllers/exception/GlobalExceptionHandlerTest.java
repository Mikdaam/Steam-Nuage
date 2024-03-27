package fr.side.projects.steamnuage.controllers.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
  @InjectMocks
  private GlobalExceptionHandler globalExceptionHandler;

  @Test
  public void testHandleResourceNotFoundException() {
    // Create a sample ResourceNotFoundException
    var ex = new ResourceNotFoundException("Game with id[-1] doesn't exist");
    var expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        ex.getMessage()));
    var actualResponse = globalExceptionHandler.handleResourceNotFoundException(ex);

    assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    ErrorResponse expectedResultBody = expectedResponse.getBody();
    ErrorResponse actualResultBody = actualResponse.getBody();
    if (expectedResultBody != null && actualResultBody != null) {
      assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
    }
  }

    /*@Test
    public void testHandleConstraintViolationException() {
       SupplierInputDTO supplier=new SupplierInputDTO("aa","Colombo",123);
        // Ensure that validation fails and throws ConstraintViolationException
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            supplierService.createSupplier(supplier);
        });
          ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleConstraintViolationException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          if(actualResultBody != null) {
              assertEquals("[Invalid Name: Must be of 4 - 30 characters]", responseEntity.getBody().getMessage());
          }
    }*/

  @Test
  public void testMethodArgumentTypeMismatchException() {
    var ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getMessage()).thenReturn("Given request param is not supported");
    // Call the method and assert the response
    var actualResult = globalExceptionHandler.handleMethodArgumentTypeMismatchException(ex);
    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
    var actualResultBody = actualResult.getBody();
    if (actualResultBody != null) {
      assertEquals("Given request param is not supported", actualResult.getBody().getMessage());
    }
  }

  @Test
  public void testHttpRequestMethodNotSupportedException() {
    var ex = mock(HttpRequestMethodNotSupportedException.class);
    when(ex.getMessage()).thenReturn("Request method is not supported");
    // Call the method and assert the response
    var actualResult = globalExceptionHandler.handleRequestMethodNotSupportedException(ex);
    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
    var actualResultBody = actualResult.getBody();
    if (actualResultBody != null) {
      assertEquals("Request method is not supported", actualResult.getBody().getMessage());
    }
  }

  @Test
  public void testHttpMessageNotReadableException() {
    var ex = mock(HttpMessageNotReadableException.class);
    when(ex.getMessage()).thenReturn("Could not read JSON");
    // Call the method and assert the response
    var actualResult = globalExceptionHandler.handleHttpMessageNotReadableException(ex);
    assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
    var actualResultBody = actualResult.getBody();
    if (actualResultBody != null) {
      assertEquals("Could not read JSON", actualResult.getBody().getMessage());
    }
  }

  @Test
  public void testNoHandlerFoundException() {
    var ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("Request URL is not found");
    // Call the method and assert the response
    var actualResult = globalExceptionHandler.handleNoHandlerFoundException(ex);
    assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
    var actualResultBody = actualResult.getBody();
    if (actualResultBody != null) {
      assertEquals("Request URL is not found", actualResult.getBody().getMessage());
    }
  }

  @Test
  public void testHttpMediaTypeNotSupportedException() {
    var ex = mock(HttpMediaTypeNotSupportedException.class);
    when(ex.getContentType()).thenReturn(MediaType.valueOf("application/json"));
    // Call the method and assert the response
    ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleUnsupportedMediaTypeException(ex);
    assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, actualResult.getStatusCode());
    ErrorResponse actualResultBody = actualResult.getBody();
    if (actualResultBody != null) {
      assertEquals("application/json", actualResult.getBody().getMessage());
    }
  }
}