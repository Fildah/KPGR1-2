package controller;

import mode3d.*;
import renderer.GPURenderer;
import renderer.Renderer3D;
import transforms.*;
import view.Raster;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller3D {

    private final GPURenderer renderer;
    private Mat4 model, view, projection, projectionPersp, projectionOrtho;
    private Camera camera;
    private final List<Solid> solids;
    private int startX = -3000;
    private int startY = -3000;
    private int endX, endY, diferenceX, diferenceY;

    public Controller3D(Raster raster) {
        this.renderer = new Renderer3D(raster);

        model = new Mat4Identity(); //jednotkova kdyz nic nechci delat

        camera = new Camera()
                .withPosition(new Vec3D(3, -6, 3))  // pozice kamery)
                .withAzimuth(Math.toRadians(90)) // osa x 360
                .withZenith(Math.toRadians(-20)); // osa y zenit nesmi byt max +/- 90 stupnu

        projectionPersp = new Mat4PerspRH(Math.PI / 3, (float) raster.getScreenSize().height/raster.getScreenSize().width, 0.1, 20);
        projectionOrtho = new Mat4OrthoRH(raster.getScreenSize().width/60.0, raster.getScreenSize().height/60.0, 0.1, 200);
        projection = projectionPersp;

        initListeners(raster);

        solids = new ArrayList<>();
        solids.add(new Cube());
        solids.add(new Pyramid());

        solids.add(new Cross('z'));
        solids.add(new Cross('y'));
        solids.add(new Cross('x'));

        solids.add(new BezierCurve());

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

    private void initListeners(Raster raster) {

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                diferenceX = startX - endX;
                diferenceY = startY - endY;
                if (startX != -3000 && startY != -3000 && diferenceX < 10 && diferenceX > -10 && diferenceY < 10 && diferenceY > -10) {
                    if (diferenceY > 0) {
                        Camera camera2 = camera.addZenith(Math.toRadians((float) diferenceY/10));
                        if (Math.toDegrees(camera2.getZenith()) < 90 && Math.toDegrees(camera2.getZenith()) > -90){
                            camera = camera2;
                        } else {
                            camera = camera.withZenith(Math.toRadians(90));
                        }
                        display();
                    } else if (diferenceY < 0) {
                        Camera camera2 = camera.addZenith(Math.toRadians((float) diferenceY/10));
                        if (Math.toDegrees(camera2.getZenith()) < 90 && Math.toDegrees(camera2.getZenith()) > -90){
                            camera = camera2;
                        } else {
                            camera = camera.withZenith(Math.toRadians(-90));
                        }
                        display();
                    }
                    if (diferenceX > 0) {
                        camera = camera.addAzimuth(Math.toRadians((float) diferenceX/10));
                        display();
                    } else if (diferenceX < 0) {
                        camera = camera.addAzimuth(Math.toRadians((float) diferenceX/10));
                        display();
                    }
                }
                startX = endX;
                startY = endY;
            }
        });

        raster.addKeyListener(new KeyListener() {
            private final Set<Character> pressed = new HashSet<>();

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'o') {
                    projection = projectionOrtho;
                }
                if (e.getKeyChar() == 'p') {
                    projection = projectionPersp;
                }
                if (e.getKeyChar() == 'b') {
                    solids.remove(solids.size() - 1);
                    solids.add(new BezierCurve());
                }
                if (e.getKeyChar() == 'n') {
                    solids.remove(solids.size() - 1);
                    solids.add(new CoonsCurve());
                }
                if (e.getKeyChar() == 'm') {
                    solids.remove(solids.size() - 1);
                    solids.add(new FergusonCurve());
                }
                display();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressed.add(e.getKeyChar());
                if (pressed.size() > 0) {
                    for (char charE: pressed) {
                        if (charE == 'w') {
                            camera = camera.forward(0.1);
                        }
                        if (charE == 's') {
                            camera = camera.backward(0.1);
                        }
                        if (charE == 'a') {
                            camera = camera.left(0.1);
                        }
                        if (charE == 'd') {
                            camera = camera.right(0.1);
                        }
                    }
                }
                display();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
            }
        });
    }
}
