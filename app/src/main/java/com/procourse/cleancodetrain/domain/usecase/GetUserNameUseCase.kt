import com.procourse.cleancodetrain.domain.models.UserName
import com.procourse.cleancodetrain.domain.repository.UserRepository

class GetUserNameUseCase(private val userRepository: UserRepository) {



    /*
    откуда будут браться данные - это уже отдельная тема.
    Обычно они берутся из backEnd либо из БД и т.п
     */
    fun execute(): UserName {
        return userRepository.getName()
    }
}