package renderEngine

/**
 * Create a new raw model used by the engine to render batches of vertices to the display.
 *
 * @param vaoID       ID of the VAO where the vertex data of the model is stored.
 * @param vertexCount Number of vertices of the model.
 */
data class RawModel(val vaoID: Int, val vertexCount: Int)