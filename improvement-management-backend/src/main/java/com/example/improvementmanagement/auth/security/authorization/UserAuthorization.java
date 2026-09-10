package com.example.improvementmanagement.auth.security.authorization;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;
import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import static com.example.improvementmanagement.auth.security.constants.RoleConstants.*;

@Component("userAuthorization")
public class UserAuthorization {

    private final UserUpdateRepository userUpdateRepository;
    private final UserDeleteRepository userDeleteRepository;

    public UserAuthorization(UserUpdateRepository userUpdateRepository, UserDeleteRepository userDeleteRepository) {
        this.userUpdateRepository = userUpdateRepository;
        this.userDeleteRepository = userDeleteRepository;
    }

    //UserCreate
    public boolean canCreate(Authentication authentication,CreateUserDto dto) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        if (loginUser.getRoleId() == SYSTEM_ADMIN) {
            return true;
        }

        if (loginUser.getRoleId() == INSTRUCTOR) {

            boolean sameDepartment = loginUser.getDepartmentId().equals(dto.getDepartment_id());

            boolean allowedRole = dto.getRole_id() == RELIEF || dto.getRole_id() == WORKER;

            return sameDepartment && allowedRole;
        }

        return false;
    }

    //UserUpdate
    public boolean canUpdate(Authentication authentication,Integer targetUserId,UserUpdateDto dto) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        if (loginUser.getRoleId() == SYSTEM_ADMIN) {
            return true;
        }  

        if (loginUser.getRoleId() == INSTRUCTOR) {

            Optional<UserUpdateTargetDto> targetOptional = userUpdateRepository.findById(targetUserId);

            if (targetOptional.isEmpty()) {
                return true;
            }

            UserUpdateTargetDto targetUser = targetOptional.get();

            boolean targetSameDepartment = loginUser.getDepartmentId().equals(targetUser.getDepartmentId());

            boolean requestedSameDepartment = loginUser.getDepartmentId().equals(dto.getDepartmentId());

            boolean requestedRoleAllowed = dto.getRoleId() == RELIEF || dto.getRoleId() == WORKER;

            boolean targetRoleAllowed = targetUser.getRoleId() == RELIEF || targetUser.getRoleId() == WORKER;

            return targetSameDepartment && requestedSameDepartment && requestedRoleAllowed && targetRoleAllowed;
        }
            return false;
    }

    //UserDelete
    public boolean canDelete(Authentication authentication,Integer targetUserId) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        if (loginUser.getRoleId() != SYSTEM_ADMIN && loginUser.getRoleId() != INSTRUCTOR) {
            return false;
        }

        UserDeleteTargetDto targetUser = userDeleteRepository.findById(targetUserId);

        if (targetUser == null) {
            return true;
        }

        if (loginUser.getUserId().equals(targetUserId)) {
            return false;
        }

        if (loginUser.getRoleId() == SYSTEM_ADMIN) {
            return true;
        }

        if (loginUser.getRoleId() == INSTRUCTOR) {

            boolean sameDepartment = loginUser.getDepartmentId().equals(targetUser.getDepartmentId());

            boolean targetRoleAllowed = targetUser.getRoleId() == RELIEF || targetUser.getRoleId() == WORKER;

            return sameDepartment && targetRoleAllowed;
        }

        return false;
    }

    //UserList
    public boolean canGet(Authentication authentication) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        return loginUser.getRoleId() == SYSTEM_ADMIN || loginUser.getRoleId() == INSTRUCTOR;
    }

}


