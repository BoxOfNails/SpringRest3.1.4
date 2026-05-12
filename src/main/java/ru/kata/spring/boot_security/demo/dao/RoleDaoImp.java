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
    public Role findById(int theId) {
        return entityManager.find(Role.class, theId);
    }

    @Override
    public List<Role> findAllRoles() {
        //create a query
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "from Role", Role.class);

        // return the results
        return theQuery.getResultList();
    }
}
