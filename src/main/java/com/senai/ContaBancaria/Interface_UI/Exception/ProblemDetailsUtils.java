package com.senai.ContaBancaria.Interface_UI.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.LocalDateTime;

public class ProblemDetailsUtils {

    public static ProblemDetail buildProblem(
            HttpStatus status,
            String title,
            String detail,
            String path
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(title);
        problem.setDetail(detail);
        problem.setInstance(URI.create(path));
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("application", "ContaBancariaAPI");
        return problem;
    }
}
