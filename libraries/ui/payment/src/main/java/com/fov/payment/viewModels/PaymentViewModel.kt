package com.fov.payment.viewModels



import androidx.lifecycle.ViewModel
import com.fov.domain.interactors.payment.PaymentInteractor
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
  private val paymentInteractor: PaymentInteractor
)
    :  ViewModel()  {

    private val _uiState = MutableStateFlow(PayState())
    val uiState: StateFlow<PayState> = _uiState
    fun handlePaymentEvent(event : PayEvent){
        _uiState.value = uiState.value.build {
            when (event) {
               PayEvent.DismissErrorDialog ->{

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
            }
        }
    }
}