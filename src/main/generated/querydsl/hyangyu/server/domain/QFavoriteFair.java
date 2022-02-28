package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteFair is a Querydsl query type for FavoriteFair
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteFair extends EntityPathBase<FavoriteFair> {

    private static final long serialVersionUID = 319495240L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteFair favoriteFair = new QFavoriteFair("favoriteFair");

    public final QFair fair;

    public final QFavoriteFairId favoriteFairId;

    public final QUser user;

    public QFavoriteFair(String variable) {
        this(FavoriteFair.class, forVariable(variable), INITS);
    }

    public QFavoriteFair(Path<? extends FavoriteFair> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteFair(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteFair(PathMetadata metadata, PathInits inits) {
        this(FavoriteFair.class, metadata, inits);
    }

    public QFavoriteFair(Class<? extends FavoriteFair> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fair = inits.isInitialized("fair") ? new QFair(forProperty("fair")) : null;
        this.favoriteFairId = inits.isInitialized("favoriteFairId") ? new QFavoriteFairId(forProperty("favoriteFairId")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

