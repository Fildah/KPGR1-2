package renderer;

import mode3d.Solid;
import transforms.*;
import view.Raster;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Renderer3D implements GPURenderer {

    private Raster raster;
    private Graphics2D g2;
    private Mat4 model, view, projection;


    public Renderer3D(Raster raster) {
        this.raster = raster;
        g2 = raster.getG2();
        model = new Mat4Identity();
        view = new Mat4Identity();
        projection = new Mat4Identity();
    }

    @Override
    public void clear() {
        raster.clear();

    }

    @Override
    public void draw(Solid... solids) {
        for (Solid solid: solids) {
            List<Point3D> vertices = solid.getVertices();
            List<Integer> indices = solid.getIndices();
            for (int i = 0; i < indices.size(); i += 2) {
                Point3D a = vertices.get(indices.get(i));
                Point3D b = vertices.get(indices.get(i + 1));
                transformLine(a, b, solid.getColor());
            }
        }
    }

    private void transformLine(Point3D a, Point3D b, Color color) {
        a = a.mul(model).mul(view).mul(projection);
        b = b.mul(model).mul(view).mul(projection);

        if (a.getW() > b.getW()){
            Point3D temp = a;
            a=b;
            b=temp;
        }


        if (b.getW() < 0.1){
            return;
        }

        if (a.getW() < 0.1){
            double t = (0.1-a.getW())/(b.getW()-a.getW());
            a = a.mul(1-t).add(a.mul(t));
        }

        Optional<Vec3D> dehomogA = a.dehomog();
        Optional<Vec3D> dehomogB = b.dehomog();

        if (!dehomogA.isPresent() || !dehomogB.isPresent()) return;

        Vec3D v1 = dehomogA.get();
        Vec3D v2 = dehomogB.get();

        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);

        g2.setColor(color);
        g2.drawLine(
                (int) Math.round(v1.getX()),
                (int) Math.round(v1.getY()),
                (int) Math.round(v2.getX()),
                (int) Math.round(v2.getY()));

    }

    private Vec3D transformToWindow(Vec3D vec) {
        return vec
                .mul(new Vec3D(1, -1, 1)) // Y jde nahoru a my ho chceme dolu
                .add(new Vec3D(1, 1, 0)) // posun nahoru doleva z prostred
                .mul(new Vec3D(raster.getScreenSize().width/2f, raster.getScreenSize().height/2f, 1)); // prizpusobeni nasi obrazovce
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }
}
