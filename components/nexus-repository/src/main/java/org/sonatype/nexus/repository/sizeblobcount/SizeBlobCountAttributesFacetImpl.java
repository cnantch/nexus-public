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
import java.util.HashMap;
import java.util.Map;

/**
 * @since 3.7.0
 */
@Named
public class SizeBlobCountAttributesFacetImpl extends FacetSupport implements SizeBlobCountAttributesFacet {

    private final static Logger LOGGER = LoggerFactory.getLogger(SizeBlobCountAttributesFacetImpl.class);
    // Clé des attributs de Size Blob count
    public static final String SIZE_BLOB_COUNT_KEY_ATTRIBUTES = "sizeBlobCount";
    //Clé pour la donnée blobCount
    public static final String BLOB_COUNT_KEY = "blobCount";
    //Clé pour la donnée size
    public static final String SIZE_KEY = "size";


    public long size() {
        return getAttribute(SIZE_KEY);
    }

    public long blobCount() {
        return getAttribute(BLOB_COUNT_KEY);
    }

    private long getAttribute(String attributeName) {
        if ( getRepository().
                getConfiguration()
                .getAttributes().get(SIZE_BLOB_COUNT_KEY_ATTRIBUTES) != null) {
            return (long) getRepository().
                    getConfiguration()
                    .getAttributes().get(SIZE_BLOB_COUNT_KEY_ATTRIBUTES).get(attributeName);
        }
        return 0L;
    }


    public void calculateSizeBlobCount() {
        LOGGER.trace("Repository name {} ", getRepository().getName());
        Map attributesMap = new HashMap<>();
        attributesMap.put(BLOB_COUNT_KEY,0L);
        attributesMap.put(SIZE_KEY,0L);
        if (optionalFacet(StorageFacet.class).isPresent()) {
            TransactionalStoreMetadata.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
                StorageTx storageTx = UnitOfWork.currentTx();

                //First Get the bucket
                Bucket bucket = storageTx.findBucket(getRepository());

                //Assets of the bucket
                Iterable<Asset> assets = storageTx.browseAssets(bucket);
                if (assets != null) {
                    attributesMap.put(BLOB_COUNT_KEY, storageTx.countAssets(Query.builder().where("1").eq(1).build(), Arrays.asList(getRepository())));
                    attributesMap.put(SIZE_KEY, Streams.stream(assets).mapToLong(value -> value.size()).sum());
                }
                return  null;
            });
        }
        getRepository().getConfiguration().getAttributes().put(SIZE_BLOB_COUNT_KEY_ATTRIBUTES, attributesMap);
    }
}
