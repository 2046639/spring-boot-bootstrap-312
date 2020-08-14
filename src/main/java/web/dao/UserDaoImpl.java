package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {}

    @PersistenceContext
    private EntityManager entityManager;

//    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public UserDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public void addUser(User user) {
//        Session session = this.sessionFactory.getCurrentSession();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        session.persist(user);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
//        Session session = this.sessionFactory.getCurrentSession();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
//        session.update(user);
    }

    @Override
    public void removeUser(int id) {
//        Session session = this.sessionFactory.getCurrentSession();
//        User user = (User) session.load(User.class, new Integer(id));
        User user = entityManager.find(User.class, id);

        if (user != null) {
//            session.delete(user);
            entityManager.remove(user);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
//        Session session = this.sessionFactory.getCurrentSession();
//        List<User> listOfUsers = (List<User>) session.createQuery("select distinct u from User as u join fetch u.roles").list();
//        for (User user :
//                listOfUsers) {
//            user.getRoles();
//        }

        return (List<User>) entityManager.createQuery("from User").getResultList();
//        return null;
    }

    @Override
    public User getUserById(int id) {
//        Session session = this.sessionFactory.getCurrentSession();
//        return (User) session.createQuery("select u from User u join fetch u.roles where u.id = :idParam")
//                .setParameter("idParam", id).getSingleResult();
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
//        Session session = this.sessionFactory.getCurrentSession();
//        User user = (User) session.createQuery("select u from User u join fetch u.roles where u.name = :nameParam")
//                .setParameter("nameParam", name)
//                .uniqueResult();

//        User user = entityManager.find(User.class, name);

        String hql = "FROM User user WHERE user.name= :name";
        User user = (User) entityManager.createQuery(hql).setParameter("name", name).getSingleResult();
        return user;

    }
}
