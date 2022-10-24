/**
 * Main game loop for the game engine and entry point for testing the engine.
 */
package engineTests

import org.lwjgl.opengl.Display
import renderEngine.*

fun main() {
    val loader = Loader()
    val renderer = Renderer()

    createDisplay()

    // Vertex positions for a rectangle.
    val vertices = floatArrayOf(
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f
    )

    val model: RawModel = loader.loadToVAO(vertices)

    while (!Display.isCloseRequested()) {
        renderer.prepare()
        renderer.render(model)
        updateDisplay()
    }

    loader.cleanUp()
    closeDisplay()
}
