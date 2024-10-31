package views

interface Renderable {

    fun render(props: Any? = null)
    fun middleware() {}
}