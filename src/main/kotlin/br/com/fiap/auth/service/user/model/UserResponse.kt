import br.com.fiap.auth.service.user.model.User
import java.util.*

data class UserResponse(
    val id: UUID,
    val name: String,
    val email: String
) {
    companion object {
        fun fromEntity(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                email = user.email
            )
        }
    }
}
