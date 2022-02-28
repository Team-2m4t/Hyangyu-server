package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFair is a Querydsl query type for Fair
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFair extends EntityPathBase<Fair> {

    private static final long serialVersionUID = 898495628L;

    public static final QFair fair = new QFair("fair");

    public final StringPath content = createString("content");

    public final DatePath<java.sql.Date> endDate = createDate("endDate", java.sql.Date.class);

    public final NumberPath<Long> fairId = createNumber("fairId", Long.class);

    public final StringPath holiday = createString("holiday");

    public final NumberPath<Integer> likey = createNumber("likey", Integer.class);

    public final StringPath location = createString("location");

    public final StringPath photo1 = createString("photo1");

    public final StringPath photo2 = createString("photo2");

    public final StringPath photo3 = createString("photo3");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> reviews = createNumber("reviews", Integer.class);

    public final StringPath site = createString("site");

    public final DatePath<java.sql.Date> startDate = createDate("startDate", java.sql.Date.class);

    public final StringPath title = createString("title");

    public final TimePath<java.sql.Time> weekdayClose = createTime("weekdayClose", java.sql.Time.class);

    public final TimePath<java.sql.Time> weekdayOpen = createTime("weekdayOpen", java.sql.Time.class);

    public final TimePath<java.sql.Time> weekendClose = createTime("weekendClose", java.sql.Time.class);

    public final TimePath<java.sql.Time> weekendOpen = createTime("weekendOpen", java.sql.Time.class);

    public QFair(String variable) {
        super(Fair.class, forVariable(variable));
    }

    public QFair(Path<? extends Fair> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFair(PathMetadata metadata) {
        super(Fair.class, metadata);
    }

}

