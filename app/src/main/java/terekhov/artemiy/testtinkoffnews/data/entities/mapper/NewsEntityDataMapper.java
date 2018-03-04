package terekhov.artemiy.testtinkoffnews.data.entities.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import terekhov.artemiy.testtinkoffnews.data.entities.NewsEntity;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 *
 * Mapper class used to transform {@link NewsEntity} (in the data layer) to {@link News} in the
 * domain layer.
 */

public class NewsEntityDataMapper {
    /**
     * Transform a {@link NewsEntity} into an {@link News}.
     *
     * @param entity Object to be transformed.
     * @return {@link News} if valid {@link NewsEntity} otherwise null.
     */
    public static News transform(NewsEntity entity) {
        News model = null;
        if (entity != null) {
            model = new News();
            model.setId(entity.getId());
            model.setText(entity.getText());
            model.setDate(entity.getPublicationDate() != null
                    ? entity.getPublicationDate().getDate() : 0);
        }
        return model;
    }

    /**
     * Transform a List of {@link NewsEntity} into a Collection of {@link News}.
     *
     * @param entityCollection Object Collection to be transformed.
     * @return {@link News} if valid {@link NewsEntity} otherwise null.
     */
    public static List<News> transform(Collection<NewsEntity> entityCollection) {
        final LinkedList<News> list = new LinkedList<>();
        for (NewsEntity entity : entityCollection) {
            list.add(transform(entity));
        }
        return list;
    }
}
