package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao{
    private EntityManager entityManager;

    public RoleDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Role findRoleByName(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where name=:roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Role findById(int theId) {
        Role theRole = entityManager.find(Role.class, theId);
        return theRole;
    }

    @Override
    public List<Role> findAllRoles() {
        //create a query
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "from Role", Role.class);

        // execute query and get result list
        List<Role> roles = theQuery.getResultList();

        // return the results
        return roles;
    }
}
