package di

import TypeLocalDataSource
import TypeLocalDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val typeLocalDataSourceModule = module {
    singleOf(::TypeLocalDataSourceImpl).bind<TypeLocalDataSource>()
}
