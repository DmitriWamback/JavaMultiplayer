package engine;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import engine.entities.*;
import engine.math.Vector2;
import engine.math.Vector3;
import network.Client;

public class Engine {
    
    long window;

    final Vector3 NPC_COLOR = new Vector3(1.0f, 0.0f, 0.0f);
    final Vector3 PLAYER_COLOR = new Vector3(1.0f);

    Vector2 previousPosition = new Vector2(-100.1f);
    Vector2 position = new Vector2(0);

    public static boolean shouldClose = false;

    public Engine(int WIDTH, int HEIGHT) {

        if (!GLFW.glfwInit()) {
            System.err.println("Could not initialize glfw");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Socket client", 0, 0);
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window,
                              (videoMode.width() - WIDTH)/2,
                              (videoMode.height() - HEIGHT)/2);
        
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
    }

    void keyboard() {
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) position.y += 0.01f;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) position.y -= 0.01f;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) position.x -= 0.01f;
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) position.x += 0.01f;
    }

    public void startLoop(Client client) {
        GL.createCapabilities();

        Buffer buffer = new Buffer();
        Shader shader = new Shader("src/engine/shaders/vMain.glsl", "src/engine/shaders/fMain.glsl");

        Triangle triangle = new Triangle(buffer);

        for (int i = 0; i < Client.MAX_PLAYERS; i++) {
            Client.NPCs[i] = new NPC(buffer);
        }

        shouldClose = GLFW.glfwWindowShouldClose(window);
        String previousData = "";

        while (!GLFW.glfwWindowShouldClose(window)) {

            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
            GL30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

            keyboard();
            triangle.render(shader, new Vector3(position, 0), PLAYER_COLOR);

            for (int i = 0; i < Client.MAX_PLAYERS; i++) {
                if (Client.NPCs[i].getValid()) {
                    Client.NPCs[i].render(shader, new Vector3(Client.positions[i], 0.0f), NPC_COLOR);
                }
            }
            
            String json = "VALS"+position.x+" "+position.y+" "+Client.id+"";

            if (position != previousPosition) {
                try {
                    client.sendToServer(json);
                }
                catch (IOException e) {}
            }

            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(window);
        }
        client.closeClient();
        System.exit(1);
    }
}
