package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteFestival is a Querydsl query type for FavoriteFestival
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteFestival extends EntityPathBase<FavoriteFestival> {

    private static final long serialVersionUID = 151702684L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteFestival favoriteFestival = new QFavoriteFestival("favoriteFestival");

    public final QFavoriteFestivalId favoriteFestivalId;

    public final QFestival festival;

    public final QUser user;

    public QFavoriteFestival(String variable) {
        this(FavoriteFestival.class, forVariable(variable), INITS);
    }

    public QFavoriteFestival(Path<? extends FavoriteFestival> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteFestival(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteFestival(PathMetadata metadata, PathInits inits) {
        this(FavoriteFestival.class, metadata, inits);
    }

    public QFavoriteFestival(Class<? extends FavoriteFestival> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.favoriteFestivalId = inits.isInitialized("favoriteFestivalId") ? new QFavoriteFestivalId(forProperty("favoriteFestivalId")) : null;
        this.festival = inits.isInitialized("festival") ? new QFestival(forProperty("festival")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

