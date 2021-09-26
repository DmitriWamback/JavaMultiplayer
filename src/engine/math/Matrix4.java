package engine.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4 {
    
    public Vector4 row0, row1, row2, row3;

    public Matrix4(Vector4 a, Vector4 b, Vector4 c, Vector4 d) {
        row0 = a;
        row1 = b;
        row2 = c;
        row3 = d;
    }

    public static Matrix4 translateScale(Vector3 t, Vector3 s) {
        
        return new Matrix4(
            new Vector4(s.x, 0, 0, 0),
            new Vector4(0, s.y, 0, 0),
            new Vector4(0, 0, s.z, 0),
            new Vector4(t.x, t.y, t.z, 1)
        );
    }

    public FloatBuffer store() {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);

        buf.put(row0.x);
        buf.put(row0.y);
        buf.put(row0.z);
        buf.put(row0.w);
        buf.put(row1.x);
        buf.put(row1.y);
        buf.put(row1.z);
        buf.put(row1.w);
        buf.put(row2.x);
        buf.put(row2.y);
        buf.put(row2.z);
        buf.put(row2.w);
        buf.put(row3.x);
        buf.put(row3.y);
        buf.put(row3.z);
        buf.put(row3.w);

        return buf;
    }
}
