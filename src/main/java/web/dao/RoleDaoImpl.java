package web.dao;


import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl() {
    }

//    private SessionFactory sessionFactory;
//    public RoleDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


    @Override
    public Role getRoleByName(String name) {
//        Session session =this.sessionFactory.getCurrentSession();
//        Role role = (Role) session.createQuery("from Role where name = :nameParam")
//                .setParameter("nameParam", name).getSingleResult();
        String hql = "FROM Role role WHERE role.name= :name";
        Role role = (Role) entityManager.createQuery(hql).setParameter("name", name).getSingleResult();

//        Role role = entityManager.find(Role.class, name);
        return role;
    }

    @Override
    public List<Role> getListRoles() {
//        Session session = sessionFactory.getCurrentSession();
//        List<Role> listRoles = (List<Role>) session.createQuery("select distinct name from Role").list();
        List<Role> listRoles = entityManager.createQuery("from Role").getResultList();
        return listRoles;
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
//        entityManager.flush();
    }
}
