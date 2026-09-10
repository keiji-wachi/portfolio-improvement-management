package com.example.improvementmanagement.auth.authorization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.auth.security.authorization.UserAuthorization;
import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;

@ExtendWith(MockitoExtension.class)
class UserAuthorizationTest {

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails loginUser;

    @Mock
    private CreateUserDto dto;

    private UserAuthorization userAuthorization;

    @Mock
    private UserUpdateRepository userUpdateRepository;

    @Mock
    private UserUpdateDto updateDto;

    @Mock
    private UserDeleteRepository userDeleteRepository;

    @Mock
    private UserDeleteTargetDto deleteTargetDto;
    
    @BeforeEach
    void setUp() {

        userAuthorization = new UserAuthorization(userUpdateRepository, userDeleteRepository);

        when(authentication.getPrincipal()).thenReturn(loginUser);
    }


        //UserCreate

    @Test
    void システム管理者はユーザーを登録できる() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertTrue(result);
    }

    @Test
    void 指導員は自部署のリリーフユーザーを登録できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(dto.getDepartment_id())
                .thenReturn(1);

        when(dto.getRole_id())
                .thenReturn(3);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertTrue(result);
    }

    @Test
    void 指導員は自部署の作業者ユーザーを登録できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(dto.getDepartment_id())
                .thenReturn(1);

        when(dto.getRole_id())
                .thenReturn(4);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertTrue(result);
    }

    @Test
    void 指導員は他部署のユーザーを登録できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(dto.getDepartment_id())
                .thenReturn(2);

        when(dto.getRole_id())
                .thenReturn(4);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertFalse(result);
    }

    @Test
    void 指導員はシステム管理者ロールを登録できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(dto.getDepartment_id())
                .thenReturn(1);

        when(dto.getRole_id())
                .thenReturn(1);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertFalse(result);
    }

    @Test
    void 指導員は指導員ロールを登録できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(dto.getDepartment_id())
                .thenReturn(1);

        when(dto.getRole_id())
                .thenReturn(2);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertFalse(result);
    }

    @Test
    void リリーフはユーザーを登録できない() {

        when(loginUser.getRoleId())
                .thenReturn(3);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertFalse(result);
    }

    @Test
    void 作業者はユーザーを登録できない() {

        when(loginUser.getRoleId())
                .thenReturn(4);

        boolean result =
                userAuthorization.canCreate(authentication, dto);

        assertFalse(result);
    }

    //UserUpdate

    @Test
    void システム管理者はユーザーを更新できる() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertTrue(result);
    }


    @Test
    void 指導員は自部署の作業者を更新できる() {

        when(loginUser.getRoleId())
               .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(4);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            4
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertTrue(result);
    }


    @Test
    void 指導員は他部署のユーザーを更新できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(4);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            2,
                            4
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 指導員はユーザーを他部署へ変更できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(2);

        when(updateDto.getRoleId())
                .thenReturn(4);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            4
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 指導員はユーザーをシステム管理者へ変更できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(1);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            4
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 指導員はユーザーを指導員へ変更できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(2);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            4
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 指導員はシステム管理者を更新できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(4);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            1
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 指導員は指導員を更新できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getDepartmentId())
                .thenReturn(1);

        when(updateDto.getRoleId())
                .thenReturn(4);

        when(userUpdateRepository.findById(1))
                .thenReturn(Optional.of(
                    new UserUpdateTargetDto(
                            1,
                            "EMP001",
                            "test",
                            1,
                            2
                    )
            ));

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void リリーフはユーザーを更新できない() {

        when(loginUser.getRoleId())
                .thenReturn(3);

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }


    @Test
    void 作業者はユーザーを更新できない() {

        when(loginUser.getRoleId())
                .thenReturn(4);

        boolean result =
            userAuthorization.canUpdate(
                    authentication,
                    1,
                    updateDto
            );

        assertFalse(result);
    }

    //UserDelete
    @Test
    void システム管理者は他ユーザーを削除できる() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result = 
            userAuthorization.canDelete(
                authentication, 
                2
            );

        assertTrue(result);
    }

    @Test
    void 指導員は自部署のリリーフを削除できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getRoleId())
                .thenReturn(3);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication,
                2
            );

        assertTrue(result);
    }

    @Test
    void 指導員は自部署の作業者を削除できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(loginUser.getDepartmentId())
                .thenReturn(1);
                
        when(deleteTargetDto.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getRoleId())
                .thenReturn(4);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                2
            );

        assertTrue(result);
    }

    @Test
    void 指導員は他部署ユーザーを削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getDepartmentId())
                .thenReturn(2);

        when(deleteTargetDto.getRoleId())
                .thenReturn(4);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                2
            );

        assertFalse(result);
    }

    @Test
    void 指導員はシステム管理者を削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getRoleId())
                .thenReturn(1);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                2
            );

        assertFalse(result);
    }

    @Test
    void 指導員は指導員を削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(1);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getDepartmentId())
                .thenReturn(1);

        when(deleteTargetDto.getRoleId())
                .thenReturn(2);

        when(userDeleteRepository.findById(2))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                2
            );

        assertFalse(result);
    }

    @Test
    void システム管理者は自分自身を削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        when(loginUser.getUserId())
                .thenReturn(7);

        when(userDeleteRepository.findById(7))
                .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                7
            );

        assertFalse(result);
    }

    @Test
    void 指導員は自分自身を削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getUserId())
                .thenReturn(8);

        when(userDeleteRepository.findById(8))
               .thenReturn(deleteTargetDto);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                8
            );

        assertFalse(result);
    }

    @Test
    void リリーフはユーザーを削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(3);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                9
            );

        assertFalse(result);
    }

    @Test
    void 作業者はユーザーを削除できない() {

        when(loginUser.getRoleId())
                .thenReturn(4);

        boolean result =
            userAuthorization.canDelete(
                authentication, 
                10
            );

        assertFalse(result);
    }

    //UserList
    @Test
    void システム管理者はユーザー一覧を参照できる() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        boolean result =
            userAuthorization.canGet(authentication);

        assertTrue(result);
    }

    @Test
    void 指導員はユーザー一覧を参照できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        boolean result =
            userAuthorization.canGet(authentication);

        assertTrue(result);
    }

    @Test
    void リリーフはユーザー一覧を参照できない() {

        when(loginUser.getRoleId())
                .thenReturn(3);

        boolean result =
            userAuthorization.canGet(authentication);

        assertFalse(result);
    }

    @Test
    void 作業者はユーザー一覧を参照できない() {

        when(loginUser.getRoleId())
                .thenReturn(4);

        boolean result =
            userAuthorization.canGet(authentication);

        assertFalse(result);
    }
}