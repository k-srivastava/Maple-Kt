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
        0.5f, 0.5f, 0f
    )

    // Index for rendering order of vertices for a rectangle.
    val indices = intArrayOf(
        0, 1, 3,
        3, 1, 2
    )
    val model: RawModel = loader.loadToVAO(vertices, indices)

    while (!Display.isCloseRequested()) {
        renderer.prepare()
        renderer.render(model)
        updateDisplay()
    }

    loader.cleanUp()
    closeDisplay()
}
