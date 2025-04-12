package app.interfaces

interface Renderable {

    fun render(props: Any? = null)
    fun middleware() {}
}