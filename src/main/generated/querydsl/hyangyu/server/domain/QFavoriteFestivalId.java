package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavoriteFestivalId is a Querydsl query type for FavoriteFestivalId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteFestivalId extends BeanPath<FavoriteFestivalId> {

    private static final long serialVersionUID = -242606377L;

    public static final QFavoriteFestivalId favoriteFestivalId = new QFavoriteFestivalId("favoriteFestivalId");

    public final NumberPath<Long> festivalId = createNumber("festivalId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFavoriteFestivalId(String variable) {
        super(FavoriteFestivalId.class, forVariable(variable));
    }

    public QFavoriteFestivalId(Path<? extends FavoriteFestivalId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavoriteFestivalId(PathMetadata metadata) {
        super(FavoriteFestivalId.class, metadata);
    }

}

