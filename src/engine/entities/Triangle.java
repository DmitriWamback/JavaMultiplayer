package engine.entities;

import org.lwjgl.opengl.GL30;

import engine.math.Matrix4;
import engine.math.Vector3;
import engine.Shader;

public class Triangle extends Entity {
    
    public Triangle(Buffer buf) {
        super(buf);
        buffer = buf;

        float[] _v = {
            -0.5f, -0.5f,
             0.5f, -0.5f,
             0.0f,  0.5f
        };

        vertices = _v;
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, buffer.vbo);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
        
        GL30.glBindVertexArray(buffer.vao);
        GL30.glEnableVertexAttribArray(0);
        GL30.glVertexAttribPointer(0, 2, GL30.GL_FLOAT, false, 2 * Float.BYTES, 0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void render(Shader shader, Vector3 position, Vector3 color) {
        shader.use();
        shader.setVector3("col", color);
        shader.setMatrix4("position_matrix", Matrix4.translateScale(position, new Vector3(0.2f)));

        GL30.glBindVertexArray(buffer.vao);
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 3);
    }
}
