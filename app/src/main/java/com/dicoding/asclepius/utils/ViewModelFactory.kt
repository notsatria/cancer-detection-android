import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.HeadlineNewsRepository
import com.dicoding.asclepius.viewmodel.DashboardViewModel

class ViewModelFactory private constructor(
    private val repository: HeadlineNewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DashboardViewModel::class.java -> DashboardViewModel(repository) as T
            // Add more view models here as needed
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(repository: HeadlineNewsRepository): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(repository).also { INSTANCE = it }
            }
        }
    }
}