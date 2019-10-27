package de.fklappan.app.repositoryloader.domain.usecases.common

import io.reactivex.Single

/**
 * Interface of a UseCase without input but with output parameters.
 * @param RETURN    return type of the UseCase
 */
interface InputlessUseCase <RETURN> {
    fun execute() : Single<RETURN>
}