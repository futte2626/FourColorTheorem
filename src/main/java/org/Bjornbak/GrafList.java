package org.Bjornbak;

public class GrafList {
    public static GrafList firstVertex;
    public GrafList nextVertex;

    public GrafList() {
        if (firstVertex == null) {
            firstVertex = this;
            this.nextVertex = null;
        }
        else {
            this.nextVertex = firstVertex;
            firstVertex = this;
        }
    }
}
