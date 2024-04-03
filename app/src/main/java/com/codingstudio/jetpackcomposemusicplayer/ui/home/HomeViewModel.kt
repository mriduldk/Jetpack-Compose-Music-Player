package com.codingstudio.jetpackcomposemusicplayer.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.AddMediaItemsUseCase
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.GetSongsUseCase
import com.codingstudio.jetpackcomposemusicplayer.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val addMediaItemsUseCase: AddMediaItemsUseCase
) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onEvent(event: HomeEvent) {

        when(event) {
            HomeEvent.FetchSong -> getSong()

            else -> {
            }
        }

    }

    private fun getSong() {

        homeUiState = homeUiState.copy(loading = true)

        viewModelScope.launch {
            getSongsUseCase().catch {
                homeUiState = homeUiState.copy(
                    loading = false,
                    errorMessage = it.message
                )
            }.collect{
                homeUiState = when(it) {
                    is Resource.Success -> {
                        it.data?.let { songs ->
                            addMediaItemsUseCase(songs)
                        }

                        homeUiState.copy(
                            loading = false,
                            songs = it.data
                        )
                    }

                    is Resource.Loading -> {
                        homeUiState.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Error -> {
                        homeUiState.copy(
                            loading = false,
                            errorMessage = it.message
                        )
                    }
                }
            }
        }



    }


}