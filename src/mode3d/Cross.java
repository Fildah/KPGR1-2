package mode3d;

import transforms.Point3D;

import java.awt.*;

public class Cross extends Solid{

    public Cross(char axis) {
        switch (axis){
            case 'x':
            {
                this.color = Color.RED;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(1, 0, 0));
                vertices.add(new Point3D(0.75, 0.10, 0));
                vertices.add(new Point3D(0.75, -0.10, 0));

                addIndices(0, 1, 1, 2, 1, 3);
                break;
            }
            case 'y':
            {
                this.color = Color.GREEN;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(0, 1, 0));
                vertices.add(new Point3D(0.10, 0.75, 0));
                vertices.add(new Point3D(-0.10, 0.75, 0));

                addIndices(0, 1, 1, 2, 1, 3);
                break;
            }
            case 'z':
            {
                this.color = Color.BLUE;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(0, 0, 1));
                vertices.add(new Point3D(0.10, 0, 0.75));
                vertices.add(new Point3D(-0.10, 0, 0.75));

                addIndices(0, 1, 1, 2, 1, 3);
                break;
            }
        }
    }
}
