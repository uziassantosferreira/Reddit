import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.domain.exception.DomainThrowable
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.presentation.model.Post as PresentationPost
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.presentation.data.repository.PostsRepositoryImpl
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.presentation.util.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import org.amshove.kluent.*
import org.junit.Rule
import org.junit.Test
import java.util.Date

class PostsRepositoryImplTest {

    @Rule
    @JvmField
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val getPosts: UseCase<GetPostByCommunityRequestValue,
            Pair<Pagination, List<Post>>> = mock()

    private val factory = PostsDataSourceFactory(getPosts)

    private val compositeDisposable = CompositeDisposable()

    private val repository = PostsRepositoryImpl(factory, getPagedListConfig()).apply {
        setCompositeDisposable(compositeDisposable)
    }

    @Test
    fun `should get posts and expected failed`() {
        When calling getPosts.run() itReturns Flowable.error(DomainThrowable())

        val listing = repository.getPosts()
        getPagedList(listing)
        getNetworkState(repository.getNetworkState()).failure shouldBe Failure.Generic
    }

    @Test
    fun `should get posts and expected empty list`() {
        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf()))

        val listing = repository.getPosts()
        val pagedList = getPagedList(listing)

        pagedList.shouldBeEmpty()
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }

    @Test
    fun `should get posts and expected list`() {
        val date = Date(1000)
        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf(Post(date = date))))

        val listing = repository.getPosts()
        val pagedList = getPagedList(listing)

        pagedList shouldContainAll listOf(PresentationPost(date = date))
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }

    @Test
    fun `should retry and expected list`() {
        When calling getPosts.run() itReturns Flowable.error(DomainThrowable())
        val listing = repository.getPosts()
        getPagedList(listing)
        getNetworkState(repository.getNetworkState()).status shouldBe Status.FAILED

        val date = Date(1000)
        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf(Post(date = date))))
        repository.retry()
        val pagedList = getPagedList(listing)
        pagedList shouldContainAll listOf(PresentationPost(date = date))
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }
    
    @Test
    fun `should refresh and expected list`() {
        val date = Date(1000)
        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf(Post(date = date))))
        val listing = repository.getPosts()

        val pagedList = getPagedList(listing)
        pagedList shouldContainAll listOf(PresentationPost(date = date))
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS

        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf(Post(date = date),
            Post(date = date))))

        repository.refresh()

        pagedList shouldContainAll listOf(PresentationPost(date = date),PresentationPost(date = date))
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }

    private fun getPagedList(listing: LiveData<PagedList<PresentationPost>>): PagedList<PresentationPost> {
        val observer = LoggingObserver<PagedList<PresentationPost>>()
        listing.observeForever(observer)
        observer.value.shouldNotBeNull()
        return observer.value!!
    }

    private fun getNetworkState(listing: LiveData<NetworkState>) : NetworkState {
        val networkObserver = LoggingObserver<NetworkState>()
        listing.observeForever(networkObserver)
        networkObserver.value.shouldNotBeNull()
        return networkObserver.value!!
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(1)
            .setInitialLoadSizeHint(1)
            .setEnablePlaceholders(false)
            .build()

    private class LoggingObserver<T> : Observer<T> {
        var value : T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}
