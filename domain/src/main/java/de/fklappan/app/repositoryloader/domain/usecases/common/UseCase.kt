package de.fklappan.app.repositoryloader.domain.usecases.common

import io.reactivex.Single

/**
 * Interface of a UseCase with both input and output parameters.
 * The UseCase can be started with the execute method.
 *
 * @param PARAMETER     the input parameters type
 * @param RETURN        the output parameters type
 */
interface UseCase <PARAMETER, RETURN> {
    fun execute(param: PARAMETER): Single<RETURN>
}