package de.fklappan.app.repositoryloader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import de.fklappan.app.repositoryloader.common.GuiModelMapper
import de.fklappan.app.repositoryloader.common.UseCasesFactory
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.domain.usecases.ToggleBookmarkUseCase
import de.fklappan.app.repositoryloader.ui.detailviewrepository.DetailviewRepositoryState
import de.fklappan.app.repositoryloader.ui.detailviewrepository.DetailviewRepositoryViewModel
import io.mockk.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailviewRepositoryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // using trampoline for FIFO of thread scheduling
    private val scheduler = Schedulers.trampoline()
    private val factory = mockk<UseCasesFactory>()
    private val toggleBookmarkUseCase = mockk<ToggleBookmarkUseCase>()

    private lateinit var uut : DetailviewRepositoryViewModel
    private val stateObserver = mockk<Observer<DetailviewRepositoryState>>()

    @Before
    fun setup() {
        uut = DetailviewRepositoryViewModel(factory, scheduler, scheduler, GuiModelMapper())
        uut.state.observeForever(stateObserver)

        every { stateObserver.onChanged(any())} just Runs

        uut.init(1, "RxRedux", 476, true)
    }

    @Test
    fun `test toggle bookmark expecting two updates`() {

        // given
        val domainModel =
            RepositoryDomainModel(
                1,
                "RxRedux",
                476,
                true
            )
        val guiModel = GuiModelMapper().mapDomainToGui(domainModel)

        every { factory.createToggleBookmarkUseCase() } returns toggleBookmarkUseCase
        every { toggleBookmarkUseCase.execute(domainModel) } returns Single.just(domainModel)

        // when
        uut.bookmarkClicked()

        //then
        verifySequence {
            // first update for init
            stateObserver.onChanged(DetailviewRepositoryState.RepositoryDetails(guiModel))
            // second update for bookmark toggle
            stateObserver.onChanged(DetailviewRepositoryState.RepositoryDetails(guiModel))
        }
    }
}
