package com.demo.algodemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.demo.algodemo.model.Article
import com.demo.algodemo.model.NewsResponse
import com.demo.algodemo.services.EndPoints
import com.demo.algodemo.services.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.otaliastudios.zoom.ZoomImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomFragment : BottomSheetDialogFragment() {
    private var mProgressBar: ProgressBar? = null
    private var imageView: ZoomImageView? = null

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val bottomSheet = dialog!!.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.isNestedScrollingEnabled = true
            bottomSheet?.let { i ->
                val behaviour = BottomSheetBehavior.from(i)
                setupFullHeight(i)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    /**
     * set the height of the sheet to full View
     */
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.progressBar)
        imageView = view.findViewById(R.id.imageView)

        getNewsData()
    }

    private fun getNewsData() {

        mProgressBar!!.visibility = View.VISIBLE
        val endPoints = RetrofitClient(requireContext()).client!!.create(EndPoints::class.java)
        val call = endPoints.getNews()
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                mProgressBar!!.visibility = View.GONE
                if (response.isSuccessful) {

                    val newsResponse = response.body() as NewsResponse
                    setNewsView(newsResponse.articles as ArrayList<Article>)

                }

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                mProgressBar!!.visibility = View.GONE
            }
        })
    }

    private fun setNewsView(arrayList: ArrayList<Article>) {
        Glide.with(requireActivity()).load(arrayList[0].urlToImage).into(imageView!!)
    }

}