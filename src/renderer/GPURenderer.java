package renderer;

import mode3d.Solid;
import transforms.Mat4;

public interface GPURenderer {

    void clear();

    void draw(Solid... solids);

    void setModel(Mat4 model);
    // transforamce v tomto poradi u modelu: scale -> rotace -> posunuti

    void setView(Mat4 view);

    void setProjection(Mat4 projection);
}
