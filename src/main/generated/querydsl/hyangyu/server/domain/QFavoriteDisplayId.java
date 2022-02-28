package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavoriteDisplayId is a Querydsl query type for FavoriteDisplayId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteDisplayId extends BeanPath<FavoriteDisplayId> {

    private static final long serialVersionUID = -2003285287L;

    public static final QFavoriteDisplayId favoriteDisplayId = new QFavoriteDisplayId("favoriteDisplayId");

    public final NumberPath<Long> displayId = createNumber("displayId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFavoriteDisplayId(String variable) {
        super(FavoriteDisplayId.class, forVariable(variable));
    }

    public QFavoriteDisplayId(Path<? extends FavoriteDisplayId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavoriteDisplayId(PathMetadata metadata) {
        super(FavoriteDisplayId.class, metadata);
    }

}

