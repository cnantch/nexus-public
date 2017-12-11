package org.sonatype.nexus.repository.sizeblobcount;

/**
 * Class used for storing the size and the blobCount
 */
public final class SizeBlobCount{


    private final long size;
    private final long blobCount;

    public SizeBlobCount(long size, long blobCount) {
        this.size = size;
        this.blobCount = blobCount;
    }


    public long getSize() {
        return size;
    }

    public long getBlobCount() {
        return blobCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SizeBlobCount that = (SizeBlobCount) o;

        if (size != that.size) return false;
        return blobCount == that.blobCount;
    }

    @Override
    public int hashCode() {
        int result = (int) (size ^ (size >>> 32));
        result = 31 * result + (int) (blobCount ^ (blobCount >>> 32));
        return result;
    }
}
