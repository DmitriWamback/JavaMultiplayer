package engine.entities;
import org.lwjgl.opengl.GL30;

public class Buffer {

    public final int vao, vbo;

    public Buffer() {
        vao = GL30.glGenVertexArrays();
        vbo = GL30.glGenBuffers();
    }
}