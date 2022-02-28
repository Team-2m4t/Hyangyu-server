package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteDisplay is a Querydsl query type for FavoriteDisplay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteDisplay extends EntityPathBase<FavoriteDisplay> {

    private static final long serialVersionUID = -1101524706L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteDisplay favoriteDisplay = new QFavoriteDisplay("favoriteDisplay");

    public final QDisplay display;

    public final QFavoriteDisplayId favoriteDisplayId;

    public final QUser user;

    public QFavoriteDisplay(String variable) {
        this(FavoriteDisplay.class, forVariable(variable), INITS);
    }

    public QFavoriteDisplay(Path<? extends FavoriteDisplay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteDisplay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteDisplay(PathMetadata metadata, PathInits inits) {
        this(FavoriteDisplay.class, metadata, inits);
    }

    public QFavoriteDisplay(Class<? extends FavoriteDisplay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.display = inits.isInitialized("display") ? new QDisplay(forProperty("display")) : null;
        this.favoriteDisplayId = inits.isInitialized("favoriteDisplayId") ? new QFavoriteDisplayId(forProperty("favoriteDisplayId")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

