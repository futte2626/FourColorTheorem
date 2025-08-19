package org.Bjornbak;

import java.awt.Color;
import java.util.Objects;

public class Vertex {
    String name;
    int x, y;
    Color c;

    public Vertex(String name, int x, int y, Color c) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex other = (Vertex) obj;
        return this.x == other.x && this.y == other.y &&
                Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }
}
