package engine.math;

public class Vector3 {
    
    public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector2 a, float b) {
        x = a.x;
        y = a.y;
        z = b;
    }

    public Vector3(float a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }
}
