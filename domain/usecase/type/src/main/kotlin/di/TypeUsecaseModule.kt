package di

import GetActiveTypeUC
import GetActiveTypeUCImpl
import HasReachedMaxActiveTypesUC
import HasReachedMaxActiveTypesUCImpl
import IsTypeActiveUC
import IsTypeActiveUCImpl
import RemoveOldestTypeUC
import RemoveOldestTypeUCImpl
import SetActiveTypeUC
import SetActiveTypeUCImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val typeUsecaseModule = module {
    singleOf(::GetActiveTypeUCImpl).bind<GetActiveTypeUC>()
    singleOf(::IsTypeActiveUCImpl).bind<IsTypeActiveUC>()
    singleOf(::SetActiveTypeUCImpl).bind<SetActiveTypeUC>()
    singleOf(::RemoveOldestTypeUCImpl).bind<RemoveOldestTypeUC>()
    singleOf(::HasReachedMaxActiveTypesUCImpl).bind<HasReachedMaxActiveTypesUC>()

}