package com.knasirayaz.gittrends

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.presentation.home.TrendingRepoListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListFeature {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Mock
    lateinit var mObserver : Observer<ResultStates>

    lateinit var mViewModel : TrendingRepoListViewModel

    //Need to add Coroutine test to add StandardTestDispatcher.
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        mViewModel = TrendingRepoListViewModel()
        mViewModel.getTrendingListObserver().observeForever(mObserver)
        mViewModel.getTrendingRepoList()

    }

    //Acceptance Test
    @Test
    fun `fetch trending repo list`() = runTest{
        /*Todo:
           We will have observer (livedata) to observe when state changes.
            - Need to mock observer (livedata).
            - We will have a onSuccess, OnFailed and OnLoading states.
            - verify that its state is changing
         Todo:
            - Create ViewModel and change observer state from there.
            - When calling getTrendingRepoList method mObserver should change its state.
         Todo:
            - Add Loading States
            - Verify Loading states are changing when we fetch list.
           */

        launch {
            inOrder(mObserver){
                verify(mObserver).onChanged(ResultStates.Loading(true))
                verify(mObserver).onChanged(ResultStates.Success(listOf()))
                verify(mObserver).onChanged(ResultStates.Loading(false))
            }
            
        }

    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}