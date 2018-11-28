package com.uziassantosferreira.reddit.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.presentation.viewmodel.CommentsViewModel
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.setProperty
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.DateFormat
import android.text.method.LinkMovementMethod
import com.uziassantosferreira.reddit.util.customtab.CustomTabsOnClickListener
import android.text.SpannableString
import android.text.Spannable
import android.text.Spanned
import android.util.Patterns
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.uziassantosferreira.reddit.extension.load
import com.uziassantosferreira.reddit.util.CustomClickURLSpan
import com.uziassantosferreira.reddit.util.customtab.CustomTabActivityHelper

class DetailFragment: BaseFragment() {

    companion object {
        const val PROPERTY_PAGED_LIST = "pagedListDetail"
        private const val DURATION_TRANSITION = 380L
        private const val DELAY_TRANSITION = 200L
    }

    private val commentsViewModel: CommentsViewModel by viewModel()

    private val commentsAdapter: CommentsAdapter by inject { parametersOf({commentsViewModel.retry()}) }

    private val post  by lazy {  DetailFragmentArgs.fromBundle(arguments).post }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setProperty(PROPERTY_PAGED_LIST, getPagedListConfig())
        if (post.getImageUrl().isNotEmpty()){
            postponeEnterTransition()
            setTransitions()
        }

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun setTransitions() {
        val inSet = TransitionSet()
        val inflater = TransitionInflater.from(requireContext())
        val transition = inflater.inflateTransition(R.transition.arc)
        inSet.apply {
            addTransition(transition)
            duration = DURATION_TRANSITION
            interpolator = AccelerateDecelerateInterpolator()
        }
        val outSet = TransitionSet()
            .apply {
                addTransition(transition)
                duration = DURATION_TRANSITION
                interpolator = AccelerateDecelerateInterpolator()
                startDelay = DELAY_TRANSITION
            }

        sharedElementEnterTransition = inSet
        sharedElementReturnTransition = outSet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        subscribeLiveData()
        setUpCollapsingToolbar()
        fillFields()
        setUpToolbar()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buttonRetry.setOnClickListener { commentsViewModel.retry() }
        buttonShare.setOnClickListener { share() }
    }

    private fun share() {
        ShareCompat.IntentBuilder.from(activity)
            .setType("text/plain")
            .setChooserTitle(R.string.app_name)
            .setText(post.link)
            .startChooser()
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }
    private fun fillFields() {
        textViewText.visibility = if (post.text.isEmpty()) View.GONE else View.VISIBLE
        addSupportToOpenChromeTab(post.text)

        imageView.transitionName = post.remoteId

        if (post.imagePreview.isNotEmpty() && post.imagePreview.last().url.isNotEmpty()){
            val image = post.imagePreview.last()
            imageView.load(image.url, true) {
                startPostponedEnterTransition()
            }
        }else {
            lockAppBarOpen()
        }
    }

    private fun lockAppBarOpen() {
        appBarLayout.setExpanded(false, false)
        appBarLayout.isActivated = false
        ViewCompat.setNestedScrollingEnabled(nestedScrollView, false)
    }

    private fun addSupportToOpenChromeTab(text: String) {
        val spannable = SpannableString(text)
        linkUrls(spannable, CustomTabsOnClickListener(requireActivity(), CustomTabActivityHelper()))
        textViewText.text = spannable
        textViewText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun linkUrls(spannable: Spannable, onClickListener: CustomClickURLSpan.OnClickListener) {
        val matcher = Patterns.WEB_URL.matcher(spannable)
        while (matcher.find()) {
            val url = spannable.toString().substring(matcher.start(), matcher.end())
            val urlSpan = CustomClickURLSpan(url)
            urlSpan.setOnClickListener(onClickListener)
            spannable.setSpan(urlSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    private fun setUpCollapsingToolbar() {
        collapsingToolbarLayout.title = post.title
        collapsingToolbarLayout.subtitle = DateFormat.getDateInstance(DateFormat.FULL).format(post.date)
    }

    private fun initAdapter() {
        recyclerView.adapter = commentsAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun subscribeLiveData() {
        commentsViewModel.getComments(post).observe(this,
            Observer<PagedList<Comment>> { commentsAdapter.submitList(it) })
        commentsViewModel.getNetworkState().observe(this,
            Observer<NetworkState> {
                if (commentsAdapter.currentList.isNullOrEmpty()){
                    setInitialLoadingState(it)
                }else {
                    commentsAdapter.setNetworkState(it)
                }

            })
    }

    override fun setInitialLoadingState(networkState: NetworkState?) {
        super.setInitialLoadingState(networkState)
        commentsAdapter.setNetworkState(NetworkState.LOADED)
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build()
}