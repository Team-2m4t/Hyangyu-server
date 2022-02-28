package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDisplayReview is a Querydsl query type for DisplayReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisplayReview extends EntityPathBase<DisplayReview> {

    private static final long serialVersionUID = 363354706L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDisplayReview displayReview = new QDisplayReview("displayReview");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final QDisplay display;

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QUser user;

    public final NumberPath<Integer> warn = createNumber("warn", Integer.class);

    public final ListPath<DisplayWarn, QDisplayWarn> warnList = this.<DisplayWarn, QDisplayWarn>createList("warnList", DisplayWarn.class, QDisplayWarn.class, PathInits.DIRECT2);

    public QDisplayReview(String variable) {
        this(DisplayReview.class, forVariable(variable), INITS);
    }

    public QDisplayReview(Path<? extends DisplayReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDisplayReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDisplayReview(PathMetadata metadata, PathInits inits) {
        this(DisplayReview.class, metadata, inits);
    }

    public QDisplayReview(Class<? extends DisplayReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.display = inits.isInitialized("display") ? new QDisplay(forProperty("display")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

