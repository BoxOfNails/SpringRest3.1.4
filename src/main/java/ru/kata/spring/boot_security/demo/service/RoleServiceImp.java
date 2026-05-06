package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService{
    private RoleDao roleDao;
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findRoleByName(String theRoleName) {
        return roleDao.findRoleByName(theRoleName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> findByIds(Set<Integer> theIds) {
        return theIds.stream().map(id -> roleDao.findById(id)).collect(Collectors.toSet());
    }
}
