package com.voyago.config;

public class DatabaseContextHolder {
    
    private static final ThreadLocal<DatabaseOperation> contextHolder = new ThreadLocal<>();
    
    public static void setDatabaseOperation(DatabaseOperation operation) {
        contextHolder.set(operation);
    }
    
    public static DatabaseOperation getDatabaseOperation() {
        return contextHolder.get();
    }
    
    public static void clear() {
        contextHolder.remove();
    }
}
