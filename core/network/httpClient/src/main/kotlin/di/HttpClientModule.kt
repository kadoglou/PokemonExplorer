package di

import buildHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val httpClientModule = module {
    single<HttpClient> { buildHttpClient() }
}