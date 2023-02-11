package com.fdmgroup.helpdeskapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Users is the abstract base class for all users in the system
 * which allow an application to interact with messages and tickets.
 * Users state information includes:
 * <ul>
 * <li>The UserType which helps the UI with authorisation
 * <li>user_id
 * <li>username used for signing in the user
 * <li>email
 * <li>fullName
 * <li>password used for signing in the user
 * </ul>
 * 
 * @author Laney Deveson
 * @author James Giddings
 * @version %I%, %G%
 * @since 1.0
 */
@Data
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "userType")
@JsonSubTypes({ @JsonSubTypes.Type(value = Engineer.class, name = "Engineer"),
        @JsonSubTypes.Type(value = Client.class, name = "Client"),
        @JsonSubTypes.Type(value = Admin.class, name = "Admin") })
public abstract class User {
    @Id // Make this field the primary id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "generate_user")
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

}
