package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFestivalReview is a Querydsl query type for FestivalReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFestivalReview extends EntityPathBase<FestivalReview> {

    private static final long serialVersionUID = -900325800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFestivalReview festivalReview = new QFestivalReview("festivalReview");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final QFestival festival;

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QUser user;

    public final NumberPath<Integer> warn = createNumber("warn", Integer.class);

    public final ListPath<FestivalWarn, QFestivalWarn> warnList = this.<FestivalWarn, QFestivalWarn>createList("warnList", FestivalWarn.class, QFestivalWarn.class, PathInits.DIRECT2);

    public QFestivalReview(String variable) {
        this(FestivalReview.class, forVariable(variable), INITS);
    }

    public QFestivalReview(Path<? extends FestivalReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFestivalReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFestivalReview(PathMetadata metadata, PathInits inits) {
        this(FestivalReview.class, metadata, inits);
    }

    public QFestivalReview(Class<? extends FestivalReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.festival = inits.isInitialized("festival") ? new QFestival(forProperty("festival")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

