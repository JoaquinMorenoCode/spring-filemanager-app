package moreno.joaquin.filemanagerapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Size(min = 6,max = 30, message = "Sorry,your username must be between 6 and 30 characters long.")
    private String username;
    @Size(min = 8, message = "Sorry,your password must be at least 8 characters long.")

    private String password;

    private String confirmPassword;



}
