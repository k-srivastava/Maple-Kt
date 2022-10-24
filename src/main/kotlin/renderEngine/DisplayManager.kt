/**
 * Create and manage the display throughout its lifecycle.
 */
package renderEngine

import org.lwjgl.LWJGLException
import org.lwjgl.opengl.*


private const val width = 1920
private const val height = 1080
private const val fpsCap = 120

/**
 * Create the display with OpenGL 3.2 in the top-left corner of the screen with resolution of 1920x1080.
 * */
fun createDisplay() {
    val contextAttribs = ContextAttribs(3, 2)
        .withForwardCompatible(true)
        .withProfileCore(true)

    try {
        Display.setDisplayMode(DisplayMode(width, height))
        Display.create(PixelFormat(), contextAttribs)
        Display.setTitle("Maple Window")
    }
    catch (e: LWJGLException) {
        throw RuntimeException(e)
    }

    GL11.glViewport(0, 0, width, height)
}

/**
 * Update the display every frame and sync it to a maximum FPS cap.
 */
fun updateDisplay() {
    Display.sync(fpsCap)
    Display.update()
}

/**
 * Close the display at the end of the program.
 */
fun closeDisplay() {
    Display.destroy()
}

