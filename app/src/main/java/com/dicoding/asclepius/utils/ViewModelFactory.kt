import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.di.Injection
import com.dicoding.asclepius.data.repository.HeadlineNewsRepository
import com.dicoding.asclepius.viewmodel.DashboardViewModel

class ViewModelFactory private constructor(
    private val repository: HeadlineNewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DashboardViewModel::class.java -> DashboardViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(Injection.provideHeadlineNewsRepository()).also { INSTANCE = it }
            }
        }
    }
}