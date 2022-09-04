package com.fov.payment.viewModels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fov.domain.interactors.payment.PaymentInteractor
import com.fov.domain.models.payment.ProductRequest
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
  private val paymentInteractor: PaymentInteractor
)
    :  ViewModel()  {
    init{

            getStripeCredentials()

    }
    private  fun getStripeCredentials(){
        viewModelScope.launch {
            val credentials = paymentInteractor.getStripeCredentials()
            if(credentials != null) {
                _uiState.value = uiState.value.build {
                    stripeAccountId = credentials.stripeAccountId
                    stripePublishableKey = credentials.publishableKey
                }
            }
        }
    }
    private fun getStripeClientSecretId(){
        viewModelScope.launch {
            //val amount = uiState.value.amount
            val id = paymentInteractor.getStripeClientSecret(ProductRequest(
                products = emptyList()
            ))
            if(id != null) {
                _uiState.value = uiState.value.build {
                    clientStripeSecret = id
                }
            }
        }
    }
    private val _uiState = MutableStateFlow(PayState())
    val uiState: StateFlow<PayState> = _uiState
    fun handlePaymentEvent(event : PayEvent){
        _uiState.value = uiState.value.build {
            when (event) {
               PayEvent.LoadStripeClientSecret ->{
                    getStripeClientSecretId()
               }
               is PayEvent.LoadStripeWidget ->{
                    cardInputWidget = event.cardInputWidget
                }
                is PayEvent.LoadStripePaymentMethod ->{
                    stripePaymentMethod = event.method
                }
                is PayEvent.LoadClientStripeSecret ->{
                    clientStripeSecret = event.secret
                }
                is PayEvent.LoadPublishableStripeKey -> {
                    stripePublishableKey = event.key
                }
                is PayEvent.LoadStripeAccountId -> {
                    stripeAccountId = event.id
                }
                is PayEvent.SetErrorText -> {
                    errorText = event.text
                }
            }
        }
    }
}