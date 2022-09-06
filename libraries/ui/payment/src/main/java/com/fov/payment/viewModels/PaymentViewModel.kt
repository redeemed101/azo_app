package com.fov.payment.viewModels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fov.navigation.NavigationManager
import com.fov.domain.interactors.payment.PaymentInteractor
import com.fov.domain.models.payment.ProductRequest
import com.fov.navigation.PaymentDirections
import com.fov.navigation.SermonsDirections
import com.fov.payment.data.METHODS
import com.fov.payment.events.PayEvent
import com.fov.payment.models.PaymentMethod
import com.fov.payment.states.PayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
  private val paymentInteractor: PaymentInteractor,
  private val navigationManager: NavigationManager
) :  ViewModel()  {

    private val _uiState = MutableStateFlow(PayState())
    val uiState: StateFlow<PayState> = _uiState
    init{

            getStripeCredentials()
            loadPaymentMethods()


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
    private fun loadPaymentMethods() {
        _uiState.value = uiState.value.build {
            paymentMethods = METHODS
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

    fun handlePaymentEvent(event : PayEvent){
        _uiState.value = uiState.value.build {
            when (event) {
               PayEvent.LoadStripeClientSecret ->{
                    getStripeClientSecretId()
               }
                PayEvent.GoToOptions ->{
                    navigationManager.navigate(PaymentDirections.options)
                }
                PayEvent.GoToStripeOptions ->{
                    navigationManager.navigate(PaymentDirections.stripe)
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