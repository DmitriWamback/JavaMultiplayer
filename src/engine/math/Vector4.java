package engine.math;

public class Vector4 {
    
    public float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(float a) {
        this.x = a;
        this.y = a;
        this.z = a;
        this.w = a;
    }

    public Vector4(Vector3 a, float b) {
        this.x = a.x;
        this.y = a.y;
        this.z = a.z;
        this.w = b;
    }
}
