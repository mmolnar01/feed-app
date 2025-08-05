package hu.klm60o.feedapp.ui.theme.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.klm60o.feedapp.ui.theme.model.Product
import hu.klm60o.feedapp.ui.theme.model.Response
import hu.klm60o.feedapp.ui.theme.repository.ProductRepository
import hu.klm60o.feedapp.ui.theme.repository.ProductsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Product view model class
//Uses manual dependency injection to inject the repository
class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    //private val _productsState = MutableStateFlow<ProductsResponse>(Response.Loading)
    //val productsState: StateFlow<ProductsResponse> = _productsState.asStateFlow()
    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState.asStateFlow()

    private var fetchJob: Job? = null

    private val productsQueue = mutableListOf<Product>()
    private var isPaused = false
    private var isStarted = false

    fun startCommand() {
        if (isStarted) {
            return
        }

        isStarted = true
        fetchJob = viewModelScope.launch {
            while (isActive) {
                productsQueue.addAll(repository.getProduct())
                if (!isPaused) {
                    _productsState.value = productsQueue
                }
                delay(5000)
            }
        }
        addCommand("Start")
    }



    fun stopCommand() {
        fetchJob?.cancel()
        fetchJob = null
        addCommand("Stop")
        isStarted = false
        isPaused = false
        productsQueue.clear()
        repository.resetSkip()
    }

    fun pauseCommand() {
        if (!isStarted || isPaused) {
            return
        }
        isPaused = true
        addCommand("Pause")
    }

    fun resumeCommand() {
        if (!isStarted || !isPaused) {
            return
        }
        _productsState.value = productsQueue
        addCommand("Resume")
    }

    fun processCommand(command: String) {
        when (command.lowercase()) {
            "start" -> startCommand()
            "stop" -> stopCommand()
            "pause" -> pauseCommand()
            "resume" -> resumeCommand()
        }
    }

    private fun addCommand(s: String) {
        val id = productsQueue.last().id + 1
        val productDataClass = Product(
            id = id,
            description = s,
            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
            isCommand = true,
            thumbnail = null
        )
        productsQueue.add(productDataClass)
        _productsState.value = productsQueue
    }
}