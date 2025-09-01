package dev.kadoglou.pokemonexplorer

import android.app.Application
import dev.kadoglou.pokemonexplorer.di.initKoin
import org.koin.android.ext.koin.androidContext

class Initialization : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@Initialization)
        }
    }
}
