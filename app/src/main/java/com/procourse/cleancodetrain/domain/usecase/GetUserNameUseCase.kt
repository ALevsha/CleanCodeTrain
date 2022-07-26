import com.procourse.cleancodetrain.domain.models.UserName

class GetUserNameUseCase {

    /*
    откуда будут браться данные - это уже отдельная тема.
    Обычно они берутся из backEnd либо из БД и т.п
     */
    fun execute(): UserName {
        return UserName(firstName = "exampleFName", lastName = "exampleLName")
    }
}