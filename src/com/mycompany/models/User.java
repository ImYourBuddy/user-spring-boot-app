package com.mycompany.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Size(min = 2, max = 25, message = "First name must contain from 2 to 25 characters")
    private String firstName;
    private String middleName;
    @Size(min = 2, max = 25, message = "Last name must contain from 2 to 25 characters")
    private String lastName;
    @Positive(message = "Age must be positive")
    private int age;
//    @Email(message = "Invalid email")
//    @Size(min = 5, message = "Invalid email")
    @Pattern(regexp = "([A-Za-z]|[0-9])([A-Za-z]|[0-9]|\\-|\\.|\\_){0,20}([A-Za-z]|[0-9])@([A-Za-z]|[0-9])([A-Za-z]|[0-9]|\\-|\\.|\\_)*([A-Za-z]|[0-9])\\.(ru|com|net|org)",
            message = "Invalid email")
    private String email;
    @Size(min = 2, message = "Enter the job name")
    private String job;
    @PositiveOrZero(message = "Incorrect salary")
    private int salary;

}
