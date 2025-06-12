package com.example.graphql.graphql.utils;

import com.example.graphql.graphql.exceptions.AccessDeniedException;
import graphql.language.StringValue;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * A custom GraphQL directive used to enforce role-based access control
 */

@Component
public class SecuredDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldDefinition field = env.getElement();
        GraphQLFieldsContainer parent = env.getFieldsContainer();

        String roleValue = ((StringValue) env.getAppliedDirective()
                .getArgument("role")
                .getArgumentValue()
                .getValue()).getValue();
        String requiredRole = "ROLE_" + roleValue;

        DataFetcher<?> originalFetcher = env.getCodeRegistry().getDataFetcher((GraphQLObjectType) parent, field);
        DataFetcher<?> authFetcher = dataFetchingEnvironment -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AccessDeniedException("You are not authorized to perform this action");
            }

            boolean hasRole = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals(requiredRole));
            System.out.println("Has required role? " + hasRole);

            if (!hasRole) {
                throw new AccessDeniedException("You are not authorized to perform this action");
            }

            return originalFetcher.get(dataFetchingEnvironment);
        };

        env.getCodeRegistry().dataFetcher((GraphQLObjectType) parent, field, authFetcher);
        return field;
    }
}
