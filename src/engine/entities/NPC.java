package engine.entities;
import org.lwjgl.opengl.GL30;

import engine.Shader;
import engine.math.*;

public class NPC extends Triangle {

    boolean isValid = false;
    
    public NPC(Buffer buf) {
        super(buf);
    }
    
    public void setValid(boolean v) {
        isValid = v;
    }

    public boolean getValid() {
        return isValid;
    }

    @Override
    public void render(Shader shader, Vector3 position, Vector3 color) {
        if (isValid) {
            shader.use();
            shader.setVector3("col", color);
            shader.setMatrix4("position_matrix", Matrix4.translateScale(position, new Vector3(0.2f)));

            GL30.glBindVertexArray(buffer.vao);
            GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 3);
        }
    }
}
