package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFairReview is a Querydsl query type for FairReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFairReview extends EntityPathBase<FairReview> {

    private static final long serialVersionUID = 132333316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFairReview fairReview = new QFairReview("fairReview");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final QFair fair;

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QUser user;

    public final NumberPath<Integer> warn = createNumber("warn", Integer.class);

    public final ListPath<FairWarn, QFairWarn> warnList = this.<FairWarn, QFairWarn>createList("warnList", FairWarn.class, QFairWarn.class, PathInits.DIRECT2);

    public QFairReview(String variable) {
        this(FairReview.class, forVariable(variable), INITS);
    }

    public QFairReview(Path<? extends FairReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFairReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFairReview(PathMetadata metadata, PathInits inits) {
        this(FairReview.class, metadata, inits);
    }

    public QFairReview(Class<? extends FairReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fair = inits.isInitialized("fair") ? new QFair(forProperty("fair")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

