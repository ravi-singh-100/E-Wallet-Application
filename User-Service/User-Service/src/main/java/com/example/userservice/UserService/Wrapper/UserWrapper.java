package com.example.userservice.UserService.Wrapper;

import com.example.userservice.UserService.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserWrapper {
    @NotBlank
    String name;
    @NotBlank
    String phoneNumber;
    @NotBlank
    @Email
    String email;
    public User to(){
        return User.builder().name(this.name).phoneNumber(this.phoneNumber).email(this.email).build();
    }
}
