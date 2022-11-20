package ru.sotn.catalogservice.repository;

import ru.sotn.catalogservice.domain.Size;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SizeRepositoryCustomImpl implements SizeRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Optional<Size> getBySizeOrCreate(Size size) {
        TypedQuery<Size> query = entityManager.createQuery("SELECT s FROM Size s " +
                "WHERE s.eurSize =: eur and s.ruSize =: ru and s.worldSize =: world", Size.class);
        query.setParameter("eur", size.getEurSize())
                .setParameter("ru", size.getRuSize())
                .setParameter("world", size.getWorldSize());
        List<Size> sizes = query.getResultList();

        if(!sizes.isEmpty()){
            return Optional.of(sizes.get(0));
        }
        else {
            return Optional.of(entityManager.merge(size));
        }

    }
}
