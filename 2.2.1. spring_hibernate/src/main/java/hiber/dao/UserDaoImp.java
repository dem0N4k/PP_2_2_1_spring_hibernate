package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public void getUserByCarModelAndSerial(String carModel, int carSeries) {
        String HQL = "select id from Car c where c.model =:carModel and c.series =:carSeries";
        Long id = (Long) sessionFactory.getCurrentSession().createQuery(HQL)
                .setParameter("carModel", carModel)
                .setParameter("carSeries", carSeries).uniqueResult();

        String HQL2 = "from User u where u.id =:Id";
        User user = (User) sessionFactory.getCurrentSession().createQuery(HQL2)
                .setParameter("Id", id).uniqueResult();
        System.out.println(user.toString());
    }

    @Override
    public List<Car> listCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }
}
