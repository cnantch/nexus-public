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

@Named
public class RepositoryAttributesFacetImpl extends FacetSupport implements RepositoryAttributesFacet{

    private final static Logger logger = LoggerFactory.getLogger(RepositoryAttributesFacetImpl.class);

    /**
     * Return the size of the repository
     * @return
     */
    public long size() {
        return getSizeAndBlobCount().getSize();
    }

    /**
     * Numbers of blob in the component
     * @return
     */
    public long blobCount() {
        return getSizeAndBlobCount().getBlobCount();
    }

    private SizeBlobCount getSizeAndBlobCount() {
        logger.trace("Repository name {} ", getRepository().getName());
        if (optionalFacet(StorageFacet.class).isPresent()) {
            return TransactionalStoreMetadata.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
                final StorageTx storageTx = UnitOfWork.currentTx();

                //First Get the bucket
                Bucket bucket = storageTx.findBucket(getRepository());

                //Assets of the bucket
                Iterable<Asset> assets = storageTx.browseAssets(bucket);


                final long blobCount = storageTx.countAssets(Query.builder().where("1").eq(1).build(), Arrays.asList(getRepository()));


                if (assets != null) {
                    return new SizeBlobCount(Streams.stream(assets).mapToLong(value -> value.size()).sum(),
                            blobCount);
                }

                return new SizeBlobCount(0,0);

            });
        }
        return new SizeBlobCount(0,0);
    }
}
