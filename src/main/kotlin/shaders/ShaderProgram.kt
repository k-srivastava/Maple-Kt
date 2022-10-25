package shaders

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.system.exitProcess


abstract class ShaderProgram(vertexFile: String, fragmentFile: String) {
    private val programID: Int
    private val vertexShaderID: Int
    private val fragmentShaderID: Int

    /**
     * Create a new shader program.
     */
    init {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
        programID = GL20.glCreateProgram()

        GL20.glAttachShader(programID, vertexShaderID)
        GL20.glAttachShader(programID, fragmentShaderID)
        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)

        this.bindAttributes()
    }

    /**
     * Start using the shader program.
     */
    fun start() {
        GL20.glUseProgram(programID)
    }

    /**
     * Stop using the shader program.
     */
    fun stop() {
        GL20.glUseProgram(0)
    }

    /**
     * Stop using the shader program and detach and delete both the vertex and fragment shaders before deleting the program.
     */
    fun cleanUp() {
        stop()
        GL20.glDetachShader(programID, vertexShaderID)
        GL20.glDetachShader(programID, fragmentShaderID)

        GL20.glDeleteShader(vertexShaderID)
        GL20.glDeleteShader(fragmentShaderID)

        GL20.glDeleteProgram(programID)
    }

    /**
     * Abstract layout to multiple variable names with specific data.
     */
    protected abstract fun bindAttributes()

    /**
     * Bind a particular attribute with a specific variable name in the shader program.
     *
     * @param attribute Attribute to be bound.
     * @param variableName Name of the variable to which the attribute is bound.
     */
    protected fun bindAttribute(attribute: Int, variableName: String?) {
        GL20.glBindAttribLocation(programID, attribute, variableName)
    }
}

fun loadShader(file: String, type: Int): Int {
    val shaderSource = StringBuilder()

    try {
        val reader = BufferedReader(FileReader(file))
        var line: String?

        while (reader.readLine().also { line = it } != null)
            shaderSource.append(line).append("\n")

        reader.close()
    }
    catch (e: IOException) {
        System.err.println("Could not read file!")
        e.printStackTrace()
        exitProcess(-1)
    }

    val shaderID = GL20.glCreateShader(type)
    GL20.glShaderSource(shaderID, shaderSource)
    GL20.glCompileShader(shaderID)

    if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
        println(GL20.glGetShaderInfoLog(shaderID, 500))
        System.err.println("Could not compile shader!")
        exitProcess(-1)
    }

    return shaderID
}
