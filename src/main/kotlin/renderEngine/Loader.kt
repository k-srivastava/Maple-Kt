package renderEngine

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer

/**
 * Load a model by putting its vertex information into a VBO which is stored in the attribute list of a VAO.
 */
class Loader {
    private var vertexAttributeObjects = mutableListOf<Int>()
    private var vertexBufferObjects = mutableListOf<Int>()

    /**
     * Create a new VAO and bind it for use.
     *
     * @return ID of the newly created VAO.
     */
    private fun createVAO(): Int {
        val vaoID: Int = GL30.glGenVertexArrays()
        vertexAttributeObjects.add(vaoID)

        GL30.glBindVertexArray(vaoID)
        return vaoID
    }

    /**
     * Unbind the newly created VAO.
     */
    private fun unbindVAO() {
        GL30.glBindVertexArray(0)
    }

    /**
     * Store the vertex data in an attribute list in a particular slot.
     *
     * @param attributeNumber Slot number of where data is stored.
     * @param data            Vertex data to be stored in an attribute list.
     */
    private fun storeDataInAttributeList(attributeNumber: Int, data: FloatArray) {
        val vboID: Int = GL15.glGenBuffers()
        vertexBufferObjects.add(vboID)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID)

        val buffer = storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    /**
     * Store the vertex data in a new float buffer.
     *
     * @param data Vertex data to be stored in a new float buffer.
     * @return Float buffer with vertex data.
     */
    private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()

        return buffer
    }

    /**
     * Load a model's vertex positions in a VAO.
     *
     * @param positions Vertex positions of the model.
     * @return Raw model stored in a VAO.
     */
    fun loadToVAO(positions: FloatArray): RawModel {
        val vaoID: Int = createVAO()
        storeDataInAttributeList(0, positions)
        unbindVAO()

        return RawModel(vaoID, positions.size)
    }

    /**
     * Delete all created VAOs and VBOs.
     */
    fun cleanUp() {
        for (vao in vertexAttributeObjects) {
            GL30.glDeleteVertexArrays(vao)
        }

        for (vbo in vertexBufferObjects) {
            GL15.glDeleteBuffers(vbo)
        }
    }
}