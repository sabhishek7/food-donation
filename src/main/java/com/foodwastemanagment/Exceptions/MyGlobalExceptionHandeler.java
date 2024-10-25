package com.foodwastemanagment.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationException(MethodArgumentNotValidException e, Model model) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        });

        model.addAttribute("error", "Validation errors occurred: " + errors);
        return new ModelAndView("error"); // Returns the "error.html" view
    }

    @ExceptionHandler(APIException.class)
    public ModelAndView handleAPIException(APIException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return new ModelAndView("error"); // Returns the "error.html" view
    }
}
