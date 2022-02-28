package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavoriteFairId is a Querydsl query type for FavoriteFairId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteFairId extends BeanPath<FavoriteFairId> {

    private static final long serialVersionUID = 2092249987L;

    public static final QFavoriteFairId favoriteFairId = new QFavoriteFairId("favoriteFairId");

    public final NumberPath<Long> fairId = createNumber("fairId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFavoriteFairId(String variable) {
        super(FavoriteFairId.class, forVariable(variable));
    }

    public QFavoriteFairId(Path<? extends FavoriteFairId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavoriteFairId(PathMetadata metadata) {
        super(FavoriteFairId.class, metadata);
    }

}

