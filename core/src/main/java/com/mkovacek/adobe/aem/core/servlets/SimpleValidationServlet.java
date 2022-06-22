package com.mkovacek.adobe.aem.core.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(service = Servlet.class)
@SlingServletPaths("/bin/validation")
public class SimpleValidationServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Demo demo = new Demo();
        demo.setId(1);
        demo.setEmail("name.surname");

        Set<ConstraintViolation<Demo>> constraintViolations = validator.validate(demo);

        if (!constraintViolations.isEmpty()) {
            List<ConstraintViolation<Demo>> violations = IteratorUtils.toList(constraintViolations.iterator());
            for (ConstraintViolation<Demo> violation : violations) {
                log.warn("Not valid {}: {}", violation.getPropertyPath().toString(), violation.getMessage());
                resp.getWriter().println(StringUtils.join(violation.getPropertyPath().toString(), ":", violation.getMessage()));
            }
        }
    }

    @Data
    class Demo {

        private long id;

        @Min(1)
        private int number;

        @Email
        private String email;

        @NotNull
        private String name;

    }

}
