package com.fotova.firstapp.repository.role;

import com.fotova.entity.ERole;
import com.fotova.entity.RoleEntity;
import com.fotova.repository.role.RoleRepositoryImpl;
import com.fotova.repository.role.RoleRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoleServiceUnitTest {
    @Mock
    private RoleRepositoryJpa roleRepositoryJpa;

    @InjectMocks
    private RoleRepositoryImpl roleRepositoryImpl;

    private RoleEntity roleEntityOne = new RoleEntity();
    private RoleEntity roleEntityTwo = new RoleEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        roleEntityOne.setId(1);
        roleEntityOne.setName(ERole.ROLE_ADMIN);

        roleEntityTwo.setId(2);
        roleEntityTwo.setName(ERole.ROLE_USER);
    }
    @Test
    @DisplayName("Find all role")
    @Order(1)
    public void givenRoleRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(roleRepositoryJpa.findAll()).willReturn(List.of(roleEntityOne, roleEntityTwo));

        //WHEN
        List<RoleEntity> roleEntityList = roleRepositoryImpl.findAll();

        // THEN
        assertThat(roleEntityList).isNotEmpty();
        assertThat(roleEntityList).hasSize(2);
        assertThat(roleEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find role by id")
    @Order(2)
    public void givenCommentRepository_whenFindById_thenReturnComment() {

        // GIVEN
        BDDMockito.given(roleRepositoryJpa.findById(1)).willReturn(Optional.ofNullable(roleEntityOne));

        // WHEN
        RoleEntity roleEntity = roleRepositoryImpl.findById(1);

        // THEN
        assertThat(roleEntity).isNotNull();
        assertThat(roleEntity.getId()).isEqualTo(1);
        assertThat(roleEntity.getName()).isEqualTo(ERole.ROLE_ADMIN);
    }

}
