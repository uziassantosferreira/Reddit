package com.uziassantosferreira.reddit.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.presentation.viewmodel.CommentsViewModel
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import com.uziassantosferreira.reddit.extension.getMessage
import com.uziassantosferreira.reddit.util.GlideApp
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
import androidx.core.view.ViewCompat
import com.uziassantosferreira.reddit.util.CustomClickURLSpan
import com.uziassantosferreira.reddit.util.customtab.CustomTabActivityHelper
import java.util.regex.Pattern


class DetailFragment: BaseFragment() {

    companion object {
        const val PROPERTY_PAGED_LIST = "pagedListDetail"
        private val URL_PATTERN = Pattern.compile("((http|https|rstp):\\/\\/\\S*)")

    }

    private val commentsViewModel: CommentsViewModel by viewModel()

    private val commentsAdapter: CommentsAdapter by inject { parametersOf({commentsViewModel.retry()}) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setProperty(PROPERTY_PAGED_LIST, getPagedListConfig())
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val post = DetailFragmentArgs.fromBundle(arguments).post

        initAdapter()
        subscribeLiveData(post)
        setUpCollapsingToolbar(post)
        fillFields(post)
        setUpToolbar()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buttonRetry.setOnClickListener { commentsViewModel.retry() }
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    private fun fillFields(post: Post) {
        textViewText.visibility = if (post.text.isEmpty()) View.GONE else View.VISIBLE
        addSupportToOpenChromeTab(post.text)

        if (post.imagePreview.isNotEmpty() && post.imagePreview.last().url.isNotEmpty()){
            val image = post.imagePreview.last()
            GlideApp.with(this)
                .load(image.url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewThumb)
        }else {
            Glide.with(this).clear(imageViewThumb)
        }
    }

    private fun addSupportToOpenChromeTab(text: String) {
        val spannable = SpannableString(text)
        linkUrls(spannable, CustomTabsOnClickListener(requireActivity(), CustomTabActivityHelper()))
        textViewText.text = spannable
        textViewText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun linkUrls(spannable: Spannable, onClickListener: CustomClickURLSpan.OnClickListener) {
        val matcher = URL_PATTERN.matcher(spannable)
        while (matcher.find()) {
            val url = spannable.toString().substring(matcher.start(), matcher.end())
            val urlSpan = CustomClickURLSpan(url)
            urlSpan.setOnClickListener(onClickListener)
            spannable.setSpan(urlSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    private fun setUpCollapsingToolbar(post: Post) {
        collapsingToolbarLayout.title = post.title
        collapsingToolbarLayout.subtitle = DateFormat.getDateInstance(DateFormat.FULL).format(post.date)
    }

    private fun initAdapter() {
        recyclerView.adapter = commentsAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun subscribeLiveData(post: Post) {
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