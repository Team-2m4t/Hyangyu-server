package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDisplayWarn is a Querydsl query type for DisplayWarn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisplayWarn extends EntityPathBase<DisplayWarn> {

    private static final long serialVersionUID = -1063162880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDisplayWarn displayWarn = new QDisplayWarn("displayWarn");

    public final QDisplayReview displayReview;

    public final QUser user;

    public final NumberPath<Long> warnId = createNumber("warnId", Long.class);

    public QDisplayWarn(String variable) {
        this(DisplayWarn.class, forVariable(variable), INITS);
    }

    public QDisplayWarn(Path<? extends DisplayWarn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDisplayWarn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDisplayWarn(PathMetadata metadata, PathInits inits) {
        this(DisplayWarn.class, metadata, inits);
    }

    public QDisplayWarn(Class<? extends DisplayWarn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.displayReview = inits.isInitialized("displayReview") ? new QDisplayReview(forProperty("displayReview"), inits.get("displayReview")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

