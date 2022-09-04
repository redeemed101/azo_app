package com.fov.payment.di


import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.interactors.payment.PaymentInteractor
import com.fov.domain.remote.payment.PaymentKtorRemote
import com.fov.domain.remote.payment.PaymentKtorService
import com.fov.domain.remote.payment.PaymentRemote
import com.fov.domain.repositories.payment.PaymentRepository
import com.fov.domain.repositories.payment.PaymentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PaymentModule {
    @Provides
    fun providesPaymentInteractor(
        repository: PaymentRepository
    ): PaymentInteractor = PaymentInteractor(repository)

    @Provides
    fun providesPaymentRepository(
        remote: PaymentRemote
    ) : PaymentRepository = PaymentRepositoryImpl(remote)
    @Provides
    fun providesPaymentRemote(
       paymentService: PaymentKtorService
    ): PaymentRemote = PaymentKtorRemote(
        paymentService
    )
}