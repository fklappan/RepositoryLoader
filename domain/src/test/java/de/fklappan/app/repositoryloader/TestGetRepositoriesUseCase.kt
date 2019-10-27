package de.fklappan.app.repositoryloader

import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.Logger
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.domain.usecases.GetRepositoriesUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class TestGetRepositoriesUseCase {

    private val repository = mockk<DataRepository>()
    private val logger = mockk<Logger>()

    @Before
    fun setup() {
        every { logger.d(any(), any()) } just Runs
        every { logger.e(any(), any()) } just Runs
        every { logger.w(any(), any()) } just Runs
    }

    @Test
    fun shouldReturnEmptyList() {
        // given
        every { repository.getRepositories() } returns ArrayList()
        every { repository.getBookmarks() } returns ArrayList()

        val uut = GetRepositoriesUseCase(repository, logger)

        // when
        val observer : TestObserver<List<RepositoryDomainModel>> = uut.execute().test()
        observer.awaitTerminalEvent()

        // then
        observer.assertNoErrors()
        observer.assertValue { list -> list.isEmpty() }
    }

    @Test
    fun shouldReturnUnbookmarkedElement() {
        // given
        val testData = ArrayList<RepositoryDomainModel>()
        testData.add(RepositoryDomainModel(1,"RxRedux",476,true))
        every { repository.getBookmarks() } returns ArrayList()
        every { repository.getRepositories() } returns testData
        val uut = GetRepositoriesUseCase(repository, logger)

        // when
        val observer : TestObserver<List<RepositoryDomainModel>> = uut.execute().test()
        observer.awaitTerminalEvent()

        // then
        observer.assertNoErrors()
        observer.assertValue { list -> list.size == 1 }
        observer.assertValue { list -> !list[0].bookmark }
    }
}
