package hu.klm60o.feedapp.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.klm60o.feedapp.ui.theme.model.Product
import hu.klm60o.feedapp.ui.theme.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    //Create the flow
    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState.asStateFlow()

    //The job which will fetch us the products
    private var fetchJob: Job? = null

    private var productsQueue = listOf<Product>()
    private var isPaused = false
    private var isStarted = false

    private var id = 0

    //Start command function
    //Launches a coroutine to get the products every 5 seconds
    private fun startCommand() {
        if (isStarted) {
            return
        }

        isStarted = true
        fetchJob = viewModelScope.launch {
            while (isActive) {
                val newProducts = withContext(Dispatchers.IO) {
                    repository.getProducts()
                }

                newProducts.let {
                    productsQueue = productsQueue + newProducts
                }

                if (!isPaused) {
                    _productsState.value += productsQueue
                    productsQueue = emptyList()
                }

                delay(5000)
            }
        }
        addCommand("Start")
    }


    //Stop command function
    //Cancels the coroutine and sets the flag to false
    private fun stopCommand() {
        fetchJob?.cancel()
        fetchJob = null

        isStarted = false
        isPaused = false

        productsQueue = emptyList()

        addCommand("Stop")

        repository.resetRepo()
    }

    //Pause command function
    private fun pauseCommand() {
        if (!isStarted || isPaused) {
            return
        }

        isPaused = true

        addCommand("Pause")
    }

    //Resume command function
    private fun resumeCommand() {
        if (!isStarted || !isPaused) {
            return
        }

        addCommand("Resume")

        isPaused = false
    }

    //Function to process the commands
    fun processCommand(command: String) {
        when (command.lowercase()) {
            "start" -> startCommand()
            "stop" -> stopCommand()
            "pause" -> pauseCommand()
            "resume" -> resumeCommand()
        }
    }

    //Adds a command to the List
    //If it is the first command, the id is zero
    private fun addCommand(s: String) {

        id = repository.getIdCounter()
        repository.incrementIdCounter()

        val newCommand = Product(
            id = id,
            description = s,
            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
            isCommand = true,
            thumbnail = null
        )

        _productsState.value = _productsState.value + newCommand + productsQueue
        productsQueue = emptyList()
    }
}