package org.sonatype.nexus.repository.sizeblobcount;

import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.transaction.UnitOfWork;

import javax.inject.Named;
import java.util.Arrays;

/**
 * @since 3.7.0
 */
@Named
public class RepositoryAttributesFacetImpl extends FacetSupport implements RepositoryAttributesFacet{

    private final static Logger logger = LoggerFactory.getLogger(RepositoryAttributesFacetImpl.class);

    private long size;

    private long blobCount;

    /**
     * Return the size of the repository
     * @return
     */
    public long size() {
        return size;
    }

    /**
     * Numbers of blob in the component
     * @return
     */
    public long blobCount() {
        return blobCount;
    }

    @Override
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public void setBlobCount(long blobCount) {
        this.blobCount = blobCount;
    }


    public SizeBlobCount calculateSizeBlobCount() {
        logger.trace("Repository name {} ", getRepository().getName());
        if (optionalFacet(StorageFacet.class).isPresent()) {
            return TransactionalStoreMetadata.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
                final StorageTx storageTx = UnitOfWork.currentTx();

                //First Get the bucket
                Bucket bucket = storageTx.findBucket(getRepository());

                //Assets of the bucket
                Iterable<Asset> assets = storageTx.browseAssets(bucket);
                long blobCount = 0;
                long size = 0;
                if (assets != null) {
                    blobCount = storageTx.countAssets(Query.builder().where("1").eq(1).build(), Arrays.asList(getRepository()));
                    size = Streams.stream(assets).mapToLong(value -> value.size()).sum();
                }
                return new SizeBlobCount(size, blobCount);
            });
        }
        return new SizeBlobCount(0,0);
    }
}
