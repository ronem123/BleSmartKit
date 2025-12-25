package com.ram.mandal.blesmartkit.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ram.mandal.blesmartkit.core.dispatcher.DispatcherProvider
import com.ram.mandal.blesmartkit.core.logger.Logger
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelper
import com.ram.mandal.blesmartkit.data.model.NewsResponse
import com.ram.mandal.blesmartkit.data.repository.NepalTrialRepository
import com.ram.mandal.blesmartkit.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: NepalTrialRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _newsResponse = MutableStateFlow<UIState<NewsResponse>>(UIState.Empty)
    init {
        fetchNews()
    }



    private fun fetchNews() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                _newsResponse.emit(
                    UIState.Failure(
                        throwable = Exception()
                    )
                )
                return@launch
            }
            _newsResponse.emit(UIState.Loading)
//            newsRepository.getNews()
//                .mapFilterCollectNews()
        }
    }



//    private suspend fun Flow<List<Article>>.mapFilterCollectNews() {
//        this.map { item ->
//            item.apply {
//                this.filter {
//                    it.title?.isNotEmpty() == true &&
//                            it.urlToImage?.isNotEmpty() == true
//                }
//            }
//        }
//            .flowOn(dispatcherProvider.io)
//            .catch {
//                _newsItem.emit(UIState.Failure(it))
//            }
//            .collect {
//                _newsItem.emit(UIState.Success(it))
//                logger.d("NewsViewModel", "Success")
//            }
//    }

}