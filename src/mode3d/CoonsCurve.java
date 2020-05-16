package mode3d;

import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;

public class CoonsCurve extends Solid {

    public CoonsCurve() {
        this(Color.BLUE);
    }

    public CoonsCurve(Color color) {
        this.color = color;
        Point3D p1 = new Point3D(3, 3, -1);
        Point3D p2 = new Point3D(3, 5, 0);
        Point3D p3 = new Point3D(5, 3, 0);
        Point3D p4 = new Point3D(5, 5, 1);
        Cubic curve = new Cubic(Cubic.COONS, p1, p2, p3, p4);
        for (int i = 0; i <= 10; i++) {
            float temp = (float)i/10;
            vertices.add(curve.compute(temp));
        }
        addIndices(0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10);
    }
}
