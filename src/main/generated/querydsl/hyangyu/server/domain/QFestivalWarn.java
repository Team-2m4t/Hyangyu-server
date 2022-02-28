package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFestivalWarn is a Querydsl query type for FestivalWarn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFestivalWarn extends EntityPathBase<FestivalWarn> {

    private static final long serialVersionUID = 1474066822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFestivalWarn festivalWarn = new QFestivalWarn("festivalWarn");

    public final QFestivalReview festivalReview;

    public final QUser user;

    public final NumberPath<Long> warnId = createNumber("warnId", Long.class);

    public QFestivalWarn(String variable) {
        this(FestivalWarn.class, forVariable(variable), INITS);
    }

    public QFestivalWarn(Path<? extends FestivalWarn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFestivalWarn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFestivalWarn(PathMetadata metadata, PathInits inits) {
        this(FestivalWarn.class, metadata, inits);
    }

    public QFestivalWarn(Class<? extends FestivalWarn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.festivalReview = inits.isInitialized("festivalReview") ? new QFestivalReview(forProperty("festivalReview"), inits.get("festivalReview")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

