package hyangyu.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBlockedUserId is a Querydsl query type for BlockedUserId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBlockedUserId extends BeanPath<BlockedUserId> {

    private static final long serialVersionUID = 1319326442L;

    public static final QBlockedUserId blockedUserId = new QBlockedUserId("blockedUserId");

    public final NumberPath<Long> reportedId = createNumber("reportedId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBlockedUserId(String variable) {
        super(BlockedUserId.class, forVariable(variable));
    }

    public QBlockedUserId(Path<? extends BlockedUserId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlockedUserId(PathMetadata metadata) {
        super(BlockedUserId.class, metadata);
    }

}

