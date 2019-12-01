package controller;

import mode3d.Cube;
import mode3d.Solid;
import renderer.GPURenderer;
import renderer.Renderer3D;
import transforms.*;
import view.Raster;

import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private final GPURenderer renderer;
    private Mat4 model, view, projection;
    private Camera camera;
    private final List<Solid> solids;
    private final Solid[] axis;

    public Controller3D(Raster raster) {
        this.renderer = new Renderer3D(raster);

        model = new Mat4Identity(); //jednotkova kdyz nic nechci delat

        camera = new Camera()
                .withPosition(new Vec3D(0.5, -6, 2))  // pozice kaemry)
                .withAzimuth(Math.toRadians(90)) // osa x 360
                .withZenith(Math.toRadians(-20)); // osa y zenit nesmi byt max +/- 90 stupnu

        projection = new Mat4PerspRH(Math.PI / 3, 600/800f, 0.1, 20);
        // upravit 600/800f v rasteru jako konstanty ktere jde volat

        // projection = new Mat4OrthoRH(800, 600, 0.1, 20); // ukazka ortho

        solids = new ArrayList<>();
        solids.add(new Cube());

        axis = new Solid[3];
        //TODO Udelat tridu (nebo jen zde implementaci) pro osy, RGB se namapuje na XYZ R->X ...

        display();
    }

    private void display() {
        renderer.clear();

        renderer.setView(camera.getViewMatrix());
        renderer.setProjection(projection);

        // vykresleni os
        //renderer.setModel(new Mat4Identity());
        // renderer.draw(axis); // na osy se neaplikuje modelovaci transformace

        // vykresleni ostatnich teles
        renderer.setModel(model);
        renderer.draw(solids.toArray(new Solid[0]));
    }
}
