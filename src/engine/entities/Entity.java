package engine.entities;

import engine.Shader;
import engine.math.Vector3;

public abstract class Entity {
    
    public float[] vertices;
    public Buffer buffer;

    public Entity(Buffer buf) {}

    public void render(Shader shader, Vector3 position, Vector3 color) {}
}
