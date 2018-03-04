package terekhov.artemiy.testtinkoffnews.data.entities.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import terekhov.artemiy.testtinkoffnews.data.entities.NewsContentEntity;
import terekhov.artemiy.testtinkoffnews.domain.model.NewsContent;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 * <p>
 * Mapper class used to transform {@link NewsContentEntity} (in the data layer) to
 * {@link NewsContent} in the domain layer.
 */

public class NewsContentEntityDataMapper {
    /**
     * Transform a {@link NewsContentEntity} into an {@link NewsContent}.
     *
     * @param entity Object to be transformed.
     * @return {@link NewsContent} if valid {@link NewsContentEntity} otherwise null.
     */
    public static NewsContent transform(NewsContentEntity entity) {
        NewsContent model = null;
        if (entity != null) {
            model = new NewsContent();
            model.setTitle(entity.getTitle() != null ? entity.getTitle().getText() : null);
            model.setContent(entity.getContent());
            model.setDate(entity.getTitle() != null
                    ? entity.getTitle().getPublicationDate() != null
                    ? entity.getTitle().getPublicationDate() != null
                    ? entity.getTitle().getPublicationDate().getDate() : 0 : 0 : 0);
        }
        return model;
    }

    /**
     * Transform a List of {@link NewsContentEntity} into a Collection of {@link NewsContent}.
     *
     * @param entityCollection Object Collection to be transformed.
     * @return {@link NewsContent} if valid {@link NewsContentEntity} otherwise null.
     */
    public static List<NewsContent> transform(Collection<NewsContentEntity> entityCollection) {
        final LinkedList<NewsContent> list = new LinkedList<>();
        for (NewsContentEntity entity : entityCollection) {
            list.add(transform(entity));
        }
        return list;
    }
}
