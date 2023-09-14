package Utility;

import javafx.scene.shape.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class ObjViewer {

    public static Mesh load(String filename) {
        TriangleMesh mesh = new TriangleMesh();
        float[] texCoords = new float[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                switch (tokens[0]) {
                    case "v":
                        mesh.getPoints().addAll(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3]));
                        break;
                    case "vt":
                        texCoords = Arrays.copyOf(texCoords, texCoords.length + 2);
                        texCoords[texCoords.length - 2] = Float.parseFloat(tokens[1]);
                        texCoords[texCoords.length - 1] = Float.parseFloat(tokens[2]);
                        break;
                    case "f":
                        for (int i = 1; i <= 3; i++) {
                            String[] faceTokens = tokens[i].split("/");
                            int vertexIndex = Integer.parseInt(faceTokens[0]) - 1;
                            int texCoordIndex = Integer.parseInt(faceTokens[1]) - 1;
                            mesh.getFaces().addAll(vertexIndex, texCoordIndex);
                            if (i == 3) {
                                // Calculate texture coordinates for the last vertex
                                float[] texCoordSum = new float[2];
                                for (int j = 0; j < 3; j++) {
                                    int prevTexCoordIndex = mesh.getFaces().get(mesh.getFaces().size() - (2 * j + 1));
                                    texCoordSum[0] += texCoords[prevTexCoordIndex];
                                    texCoordSum[1] += texCoords[prevTexCoordIndex + 1];
                                }
                                float u = texCoordSum[0] / 3f;
                                float v = texCoordSum[1] / 3f;
                                texCoords = Arrays.copyOf(texCoords, texCoords.length + 2);
                                texCoords[texCoords.length - 2] = u;
                                texCoords[texCoords.length - 1] = v;
                                mesh.getFaces().set(mesh.getFaces().size() - 1, texCoords.length / 2 - 1);

                            }
                        }
                        break;
                    default:
                        // ignore other lines
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mesh.getTexCoords().addAll(texCoords);

        return mesh;
    }

    public static Mesh loadfull(String filename) {
        TriangleMesh mesh = new TriangleMesh();
        float[] texCoords = new float[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                switch (tokens[0]) {
                    case "v":
                        mesh.getPoints().addAll(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3]));
                        break;
                    case "vt":
                        texCoords = Arrays.copyOf(texCoords, texCoords.length + 2);
                        texCoords[texCoords.length - 2] = Float.parseFloat(tokens[1]);
                        texCoords[texCoords.length - 1] = Float.parseFloat(tokens[2]);
                        break;
                    case "f":
                        int[] vertexIndices = new int[6];
                        int[] texCoordIndices = new int[6];
                        for (int i = 1; i <= 3; i++) {
                            String[] faceTokens = tokens[i].split("/");
                            int vertexIndex = Integer.parseInt(faceTokens[0]) - 1;
                            int texCoordIndex = Integer.parseInt(faceTokens[1]) - 1;
                            vertexIndices[(i - 1) * 2] = vertexIndex;
                            texCoordIndices[(i - 1) * 2] = texCoordIndex;
                        }
                        // Calculate texture coordinates for the last vertex
                        float[] texCoordSum = new float[2];
                        for (int j = 0; j < 3; j++) {
                            int prevTexCoordIndex = texCoordIndices[2 * j];
                            texCoordSum[0] += texCoords[prevTexCoordIndex];
                            texCoordSum[1] += texCoords[prevTexCoordIndex + 1];
                        }
                        float u = texCoordSum[0] / 3f;
                        float v = texCoordSum[1] / 3f;
                        texCoords = Arrays.copyOf(texCoords, texCoords.length + 2);
                        texCoords[texCoords.length - 2] = u;
                        texCoords[texCoords.length - 1] = v;
                        vertexIndices[4] = mesh.getPoints().size() / 3 - 1;
                        vertexIndices[5] = texCoords.length / 2 - 1;
                        mesh.getFaces().addAll(vertexIndices);
                        break;
                    default:
                        // ignore other lines
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
        mesh.getTexCoords().addAll(texCoords);

        return mesh;
    }
}