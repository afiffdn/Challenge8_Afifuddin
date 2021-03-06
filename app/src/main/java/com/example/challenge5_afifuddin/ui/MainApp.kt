package com.example.challenge5_afifuddin.ui

import android.app.Application
import com.example.challenge5_afifuddin.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApp)
modules(
    listOf(
        databaseModule,
        ViewModelModule,
        RepositoryModule,
        networkModule,
        datastoreModule
    )
)
        }
    }
}