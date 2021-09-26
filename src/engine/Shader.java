package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL30;

import engine.math.Matrix4;
import engine.math.Vector3;

public class Shader {
    
    public final int program;

    public Shader(String vertPath, String fragPath) {

        int vertex_shader = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
        int fragment_shader = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);

        StringBuilder vertexShaderSource = readSourceFromPath(vertPath);
        StringBuilder fragmentShaderSource = readSourceFromPath(fragPath);

        GL30.glShaderSource(vertex_shader, vertexShaderSource);
        GL30.glShaderSource(fragment_shader, fragmentShaderSource);
        GL30.glCompileShader(vertex_shader);
        GL30.glCompileShader(fragment_shader);

        program = GL30.glCreateProgram();
        GL30.glAttachShader(program, vertex_shader);
        GL30.glAttachShader(program, fragment_shader);
        GL30.glLinkProgram(program);
    }

    private int getLocation(String v) {
        return GL30.glGetUniformLocation(program, v);
    }

    public void setVector3(String variable, Vector3 vec) {
        int location = getLocation(variable);
        GL30.glUniform3f(location, vec.x, vec.y, vec.z);
    }

    public void setMatrix4(String variable, Matrix4 matr) {
        int location = getLocation(variable);
        FloatBuffer matrBuf = matr.store();
        matrBuf.flip();

        GL30.glUniformMatrix4fv(location, false, matrBuf);
    }

    private StringBuilder readSourceFromPath(String path) {

        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line).append("\n");
            }
            in.close();
        }
        catch (IOException e) {}
        return builder;
    } 

    public void use() {
        GL30.glUseProgram(program);
    }
}
