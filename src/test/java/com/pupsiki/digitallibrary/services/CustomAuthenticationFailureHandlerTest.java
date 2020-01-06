//package com.pupsiki.digitallibrary.services;
//
//import com.pupsiki.digitallibrary.models.Book;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Charge;
//import org.junit.Assert;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.AuthenticationException;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
//public class CustomAuthenticationFailureHandlerTest {
//
//
//    @Test
//    public void testOnAuthenticationFailure() throws Exception {
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        AuthenticationException exception = Mockito.mock(AuthenticationException.class);
//
//        expectedException.expect(Exception.class);
//        Locale locale = request.getLocale();
//        expectedException.expectMessage("message.badCredentials");
//        Mockito.doThrow(new Exception("message.badCredentials")).when(custom).onAuthenticationFailure(request, response, exception);
//    }