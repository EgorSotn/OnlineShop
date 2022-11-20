package ru.sotn.catalogservice.repository;

import ru.sotn.catalogservice.domain.Description;
import ru.sotn.catalogservice.domain.Size;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class DescriptionRepositoryCustomImpl implements DescriptionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Optional<Description> getByTextileOrCreate(Description description) {
        TypedQuery<Description> query = entityManager.createQuery("SELECT d FROM Description d " +
                "WHERE d.textile =: textile", Description.class);
        query.setParameter("textile", description.getTextile());

        List<Description> descriptions = query.getResultList();

        if(!descriptions.isEmpty()){
            return Optional.of(descriptions.get(0));
        }
        else {
            return Optional.of(entityManager.merge(description));
        }
    }
}
