package di

import TypeRepository
import TypeRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val typeRepositoryModule = module {
    singleOf(::TypeRepositoryImpl).bind<TypeRepository>()
}