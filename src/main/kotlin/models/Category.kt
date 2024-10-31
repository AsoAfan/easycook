package models

data class Category(
    var name: String,
    var imagUrl: String,
    var id: Int? = null,
) {

    init {
        this.id = ++_id
    }

    companion object {
        private var _id: Int = 0;
    }

}
