package de.fklappan.app.repositoryloader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import de.fklappan.app.repositoryloader.common.GuiModelMapper
import de.fklappan.app.repositoryloader.common.UseCasesFactory
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.domain.usecases.GetRepositoriesUseCase
import de.fklappan.app.repositoryloader.ui.overviewrepository.OverviewRepositoryState
import de.fklappan.app.repositoryloader.ui.overviewrepository.OverviewRepositoryViewModel
import io.mockk.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OverviewRepositoryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // using trampoline for FIFO of thread scheduling
    private val scheduler = Schedulers.trampoline()
    private val factory = mockk<UseCasesFactory>()
    private val getRepositoriesUseCase = mockk<GetRepositoriesUseCase>()

    private lateinit var uut : OverviewRepositoryViewModel
    private val stateObserver = mockk<Observer<OverviewRepositoryState>>()

    @Before
    fun setup() {
        uut = OverviewRepositoryViewModel(factory, scheduler, scheduler, GuiModelMapper())
        uut.repositoryState.observeForever(stateObserver)

        every { stateObserver.onChanged(any())} just Runs
    }

    @Test
    fun `test load repositories expecting one repository`() {

        // given
        val domainModel =
            RepositoryDomainModel(
                1,
                "RxRedux",
                476,
                true
            )
        val domainModelList = arrayListOf(domainModel)
        val guiModelList = arrayListOf(GuiModelMapper().mapDomainToGui(domainModel))

        every { factory.createGetRepositoriesUseCase() } returns getRepositoriesUseCase
        every { getRepositoriesUseCase.execute() } returns Single.just(domainModelList)

        // when
        uut.loadRepositories()

        //then
        verifySequence {
            stateObserver.onChanged(OverviewRepositoryState.Loading)
            stateObserver.onChanged(OverviewRepositoryState.RepositoryDetails(guiModelList))
        }
    }
}
