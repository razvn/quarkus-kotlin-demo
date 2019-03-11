package quarkus.demo

data class ToDo(
        val userId: Int? = null,
        val id: Int? = null,
        val title: String? = null,
        val completed: Boolean = false
)
