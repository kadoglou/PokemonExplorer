package di

import AndroidConnectivityObserver
import ConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val connectivityModule = module {

    single<ConnectivityObserver> { AndroidConnectivityObserver(androidContext()) }

}