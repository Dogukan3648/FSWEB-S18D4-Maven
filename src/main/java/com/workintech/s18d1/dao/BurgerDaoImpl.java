package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao{
    private final EntityManager entityManager;

    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class,id);

        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        return entityManager.createQuery("SELECT b FROM Burger b",Burger.class)
                .getResultList();
    }


    @Override
    public List<Burger> findByPrice(int price) {
        TypedQuery<Burger> query = entityManager.createQuery(
                "SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC",
                Burger.class
        );

        query.setParameter("price", price);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> query = entityManager.createQuery(
                "SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name ASC",
                Burger.class
        );

        query.setParameter("breadType", breadType);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query = entityManager.createQuery(
                "SELECT b FROM Burger b WHERE b.contents LIKE :content",
                Burger.class
        );

        query.setParameter("content", "%" + content + "%");
        return query.getResultList();
    }


    @Transactional
    @Override
    public Burger update(Burger burger) {
       return entityManager.merge(burger);
    }

    @Transactional
    @Override
    public Burger remove(Long id) {
        Burger burger = findById(id);
        entityManager.remove(burger);
        return burger;
    }
}
