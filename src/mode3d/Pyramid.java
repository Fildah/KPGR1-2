package mode3d;

import transforms.Point3D;

import java.awt.*;

public class Pyramid extends Solid {

    public Pyramid() {
        this(Color.GREEN);
    }

    public Pyramid(Color color) {
        this.color = color;
        vertices.add(new Point3D(1, 1, -1));
        vertices.add(new Point3D(3, 1, -1));
        vertices.add(new Point3D(3, 3, -1));
        vertices.add(new Point3D(1, 3, -1));

        vertices.add(new Point3D(2, 2, 3));

        // spodni stena
        addIndices(0, 1, 1, 2, 2, 3, 3, 0);

        // vrchni stena
        addIndices(0, 4, 1, 4, 2, 4, 3, 4);

    }
}
