 package com.voyago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
    @Column(name = "name", nullable = false)
    private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    @Column(name = "username", unique = true, nullable = false)
    private String username;
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    @Column(name = "password", nullable = false)
    private String password;
        @JsonIgnore
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    @Column(name = "role", nullable = false)
    private String role;
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    @Column(name = "status_active", nullable = false)
    private Boolean status_active;
        public Boolean getStatus_active() {
            return status_active;
        }
        public void setStatus_active(Boolean status_active) {
            this.status_active = status_active;
        }
    @Column(name = "date_created", nullable = false)
    private OffsetDateTime date_created;
        public OffsetDateTime getDate_created() {
            return date_created;
        }
        public void setDate_created(OffsetDateTime date_created) {
            this.date_created = date_created;
        }
    @Column(name = "date_updated", nullable = false)
    private OffsetDateTime date_updated;
        public OffsetDateTime getDate_updated() {
            return date_updated;
        }
        public void setDate_updated(OffsetDateTime date_updated) {
            this.date_updated = date_updated;
        }
}
