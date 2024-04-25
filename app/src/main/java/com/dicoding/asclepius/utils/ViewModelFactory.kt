import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.di.Injection
import com.dicoding.asclepius.data.repository.CancerClassificationResultRepository
import com.dicoding.asclepius.data.repository.HeadlineNewsRepository
import com.dicoding.asclepius.viewmodel.ClassificationHistoryViewModel
import com.dicoding.asclepius.viewmodel.DashboardViewModel
import com.dicoding.asclepius.viewmodel.ResultViewModel

class ViewModelFactory private constructor(
    private val headlineNewsRepository: HeadlineNewsRepository,
    private val cancerClassificationResultRepository: CancerClassificationResultRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            DashboardViewModel::class.java -> DashboardViewModel(headlineNewsRepository) as T
            ResultViewModel::class.java -> ResultViewModel(cancerClassificationResultRepository) as T
            ClassificationHistoryViewModel::class.java -> ClassificationHistoryViewModel(cancerClassificationResultRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(
                    Injection.provideHeadlineNewsRepository(),
                    Injection.provideCancerClassificationResultRepository(context)
                ).also { INSTANCE = it }
            }
        }
    }
}