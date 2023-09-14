package FrontEnd.Multithreding;

import Utility.ObjViewer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;

public class MyTask extends Task<Void> {
    String filePath;
    MeshView meshView;

    public MyTask(String path, MeshView meshView) {
        filePath = path;
        this.meshView = meshView;
    }

    @Override
    protected Void call() throws Exception {
        Mesh view = ObjViewer.load(filePath);

        Platform.runLater(() -> {
            meshView.setMesh(view);
        });
        return null;
    }

    public MeshView getMeshView() {
        return meshView;
    }

}
