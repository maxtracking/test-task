package me.alexeygusev.testtask.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import me.alexeygusev.testtask.api.ApiService
import me.alexeygusev.testtask.api.models.ApiResponse
import me.alexeygusev.testtask.api.models.Item
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class DownloadJsonUseCaseImplTest {

    @Mock
    private lateinit var apiServiceMock: ApiService

    private lateinit var downloadJsonUseCaseImpl: DownloadJsonUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        downloadJsonUseCaseImpl = DownloadJsonUseCaseImpl(apiServiceMock)
    }

    @Test
    fun `when api call returns no errors then complete sucessfully`() {
        // given
        val itemsList = listOf<Item>(
            Item(
                1,
                "title",
                "description",
                "url"
            )
        )
        val expectedResponseMock = ApiResponse(items = itemsList)

        whenever(apiServiceMock.downloadJson()).thenReturn(Single.just(expectedResponseMock))

        // when
        val testObserver = downloadJsonUseCaseImpl.execute().test()

        // then
        testObserver.assertNoErrors()
    }

    @Test
    fun `when api call returns errors then complete with error`() {
        // given
        val error = IllegalStateException("Items list cannot be empty")
        whenever(apiServiceMock.downloadJson()).thenReturn(Single.just(ApiResponse()))

        // when
        val testObserver = downloadJsonUseCaseImpl.execute().test()

        // then
        testObserver
            .assertError {
                it.javaClass == error.javaClass && it.message == error.message
            }
            .assertNoValues()
            .assertNotComplete()
    }
}
