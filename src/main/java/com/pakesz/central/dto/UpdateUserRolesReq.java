package com.pakesz.central.dto;

import com.pakesz.central.entity.ERole;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@ToString
public class UpdateUserRolesReq {
    @NotEmpty(message="Roles are required")
    private final List<ERole> roles;
}
