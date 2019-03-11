package quarkus.demo

data class ToDo(
        var userId: Int? = null,
        var id: Int? = null,
        var title: String? = null,
        var completed: Boolean = false
)
