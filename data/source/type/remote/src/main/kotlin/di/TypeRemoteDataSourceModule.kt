package di

import TypeRemoteDataSource
import TypeRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val typeRemoteDataSourceModule = module {
    singleOf(::TypeRemoteDataSourceImpl).bind<TypeRemoteDataSource>()
}
