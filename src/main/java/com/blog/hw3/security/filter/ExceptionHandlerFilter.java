package com.blog.hw3.security.filter;

import com.blog.hw3.exception.RestApiException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (IllegalArgumentException ex){
            log.error("IllegalArgumentException handler filter");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,ex);
        }

    }
    public void setErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        RestApiException errorResponse = new RestApiException();
        errorResponse.setErrorMessage(ex.getMessage());
        try{
            Gson gson = new Gson();
            String json = gson.toJson(errorResponse);
            System.out.println(json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
