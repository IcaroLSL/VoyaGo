package com.voyago.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        DatabaseOperation operation = DatabaseContextHolder.getDatabaseOperation();
        
        if (operation == null) {
            return DatabaseOperation.SELECT; // Retornar erro informando que a operação não foi definida
        }
        
        return operation;
    }
}
