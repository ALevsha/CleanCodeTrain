import com.procourse.cleancodetrain.domain.models.SaveUserNameParam

class SaveUserNameUseCase {

    /*
    лучше создавать для каждого параметра отдельную модель, т.к будет неудобно, если в 1 модели
    будет много параметров
     */
    fun execute(param: SaveUserNameParam): Boolean{
        if (param.name.isEmpty())
            return false
        else
            return true
    }
}