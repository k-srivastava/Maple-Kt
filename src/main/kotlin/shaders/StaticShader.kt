package shaders

private const val vertexFile = "src/main/kotlin/shaders/vertexShader.glsl"
private const val fragmentFile = "src/main/kotlin/shaders/fragmentShader.glsl"

class StaticShader : ShaderProgram(vertexFile, fragmentFile) {
    override fun bindAttributes() {
        super.bindAttribute(0, "position")
    }
}