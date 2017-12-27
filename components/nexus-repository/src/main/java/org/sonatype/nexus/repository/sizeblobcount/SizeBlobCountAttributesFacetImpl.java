package org.sonatype.nexus.repository.sizeblobcount;

import com.google.common.collect.Streams;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.transaction.UnitOfWork;

import javax.inject.Named;
import java.util.*;

/**
 * @since 3.7.0
 */
@Named
public class SizeBlobCountAttributesFacetImpl extends FacetSupport implements SizeBlobCountAttributesFacet {

    // Clé des attributs de Size Blob count
    public static final String SIZE_BLOB_COUNT_KEY_ATTRIBUTES = "sizeBlobCount";
    //Clé pour la donnée blobCount
    public static final String BLOB_COUNT_KEY = "blobCount";
    //Clé pour la donnée size
    public static final String SIZE_KEY = "size";


    public void calculateSizeBlobCount() {
        log.debug("Repository name {} ", getRepository().getName());
        Map<String, Object> attributesMap = new HashMap<>();
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
                    attributesMap.put(BLOB_COUNT_KEY, storageTx.countAssets(Query.builder().where("1").eq(1).build(), Collections.singletonList(getRepository())));
                    attributesMap.put(SIZE_KEY, Streams.stream(assets).mapToLong(Asset::size).sum());
                }
                return  null;
            });
        }
        Objects.requireNonNull(getRepository().getConfiguration().getAttributes())
                .put(SIZE_BLOB_COUNT_KEY_ATTRIBUTES, attributesMap);
    }
}
