package com.example.graphql.graphql.resolver;

import com.example.graphql.graphql.exceptions.AccessDeniedException;
import com.example.graphql.graphql.exceptions.IllegalArgumentException;
import com.example.graphql.graphql.exceptions.ResourceNotFoundException;
import com.example.graphql.graphql.exceptions.UnauthorizedAccessException;
import com.example.graphql.graphql.exceptions.UserAlreadyExistException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomExceptionResolver implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

        String errorMessage;

        if (exception instanceof AccessDeniedException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof UserAlreadyExistException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof ResourceNotFoundException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof IllegalArgumentException) {
            errorMessage = exception.getMessage();
        } else if (exception instanceof UnauthorizedAccessException) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "An unexpected error occurred";
        }

        return Mono.just(List.of(
                GraphqlErrorBuilder.newError(environment)
                        .message(errorMessage)
                        .errorType(ErrorType.BAD_REQUEST)
                        .build()
        ));
    }

}
