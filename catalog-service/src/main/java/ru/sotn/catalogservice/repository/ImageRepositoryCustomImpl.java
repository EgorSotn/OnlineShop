package ru.sotn.catalogservice.repository;

import ru.sotn.catalogservice.domain.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryCustomImpl implements ImageRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Image> getByUrlOrCreate(Image image) {
        TypedQuery<Image> query = entityManager.
                createQuery("select i from Image i where i.url=:url",
                        Image.class);

        query.setParameter("url", image.getUrl());


        List<Image> images = query.getResultList();

        if(images.isEmpty()){
            return Optional.of(entityManager.merge(image));
        }
        else {
            return Optional.of(images.get(0));
        }
    }
}
