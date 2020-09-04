package com.wiltech.todos.users;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User.
 * Tis entity is used by Spring security framework to validate users.
 */
@Entity
@Table(name = "user_details_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsView implements Serializable {
    static final long serialVersionUID = 5760906656094861485L;

    @Id
    private Long id;
    private Long personId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Boolean active;

    // This is not working right
//    private Set<String> roleIds;

}
