package com.example.demo.security;

import com.example.demo.controller.exceptionHandler.ApiError;
import com.example.demo.exception.CustomApiError;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (JwtException e){
            e.printStackTrace();
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e);
        }catch (RuntimeException e){
            e.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        CustomApiError apiError = new CustomApiError(status, exception);
        try {
            String jsonOutput = apiError.convertToJson();
            response.getWriter().write(jsonOutput);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
