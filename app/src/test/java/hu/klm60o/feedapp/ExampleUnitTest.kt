package hu.klm60o.feedapp

import androidx.compose.runtime.collectAsState
import hu.klm60o.feedapp.ui.theme.repository.ProductRepository
import hu.klm60o.feedapp.ui.theme.repositoryimpl.ProductRepositoryImpl
import hu.klm60o.feedapp.ui.theme.viewmodel.ProductViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var repository: ProductRepositoryImpl
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        repository = ProductRepositoryImpl(
            apiPath = "https://dummyjson.com/products",
            limit = 1,
            skip = 0
        )
        
        viewModel = ProductViewModel(
            repository = repository
        )
    }

    //Tests if the start command gets added to the list
    @Test
    fun testStartCommand() {
        viewModel.processCommand("start")
        val list = viewModel.productsState.value

        val startAdded = list.get(0).description.equals("Start")
        assertTrue("Start command should be the first", startAdded)
        assertTrue("List should have a size of 2", list.size == 2)
    }
}