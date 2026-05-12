package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImpTest {
    @Mock
    RoleDao roleDao;

    @InjectMocks
    RoleServiceImp roleService;

    @Test
    void findAllRolesShouldNotChangeList() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        roles.add(new Role("ADMIN"));
        when(roleDao.findAllRoles()).thenReturn(roles);

        List<Role> result = roleService.findAllRoles();
        assertThat(result).isEqualTo(roles);
    }

    @Test
    void findAllRolesShouldReturnEmptyListIfNotExists() {
        List<Role> roles = new ArrayList<>();
        when(roleDao.findAllRoles()).thenReturn(roles);

        List<Role> result = roleService.findAllRoles();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void findByIdsShouldReturnSet() {
        Set<Integer> theIds = new HashSet<>();
        theIds.add(1);
        theIds.add(2);
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");

        when(roleDao.findById(1)).thenReturn(role1);
        when(roleDao.findById(2)).thenReturn(role2);

        Set<Role> result = roleService.findByIds(theIds);

        assertThat(result).isNotEqualTo(null);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.contains(role1)).isEqualTo(true);
        assertThat(result.contains(role2)).isEqualTo(true);

    }
}
