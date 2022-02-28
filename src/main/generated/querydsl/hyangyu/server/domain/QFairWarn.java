package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFairWarn is a Querydsl query type for FairWarn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFairWarn extends EntityPathBase<FairWarn> {

    private static final long serialVersionUID = 491902258L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFairWarn fairWarn = new QFairWarn("fairWarn");

    public final QFairReview fairReview;

    public final QUser user;

    public final NumberPath<Long> warnId = createNumber("warnId", Long.class);

    public QFairWarn(String variable) {
        this(FairWarn.class, forVariable(variable), INITS);
    }

    public QFairWarn(Path<? extends FairWarn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFairWarn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFairWarn(PathMetadata metadata, PathInits inits) {
        this(FairWarn.class, metadata, inits);
    }

    public QFairWarn(Class<? extends FairWarn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fairReview = inits.isInitialized("fairReview") ? new QFairReview(forProperty("fairReview"), inits.get("fairReview")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

