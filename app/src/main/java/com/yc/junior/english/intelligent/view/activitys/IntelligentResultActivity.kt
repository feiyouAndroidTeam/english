package com.yc.junior.english.intelligent.view.activitys

import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.jakewharton.rxbinding.view.RxView
import com.umeng.analytics.MobclickAgent
import com.yc.junior.english.R
import com.yc.junior.english.base.utils.SimpleCacheUtils
import com.yc.junior.english.intelligent.model.domain.QuestionInfoWrapper
import com.yc.junior.english.intelligent.utils.fromHtml
import com.yc.junior.english.intelligent.view.adpaters.IntelligentResultAdapter
import com.yc.junior.english.main.model.domain.Constant
import kotlinx.android.synthetic.main.intelligent_activity_result.*
import yc.com.base.*
import java.util.concurrent.TimeUnit

/**
 * Created by zhangkai on 2017/11/28.
 */

class IntelligentResultActivity : BaseActivity<BasePresenter<BaseEngine, IView>>() {
    override fun isStatusBarMateria(): Boolean {
        return true
    }

    lateinit var adapter: IntelligentResultAdapter
    override fun init() {
        MobclickAgent.onEvent(this, "intelligent_result", "智能评测-结果")
        StatusBarCompat.light(this)
        StatusBarCompat.compat(this, mToolbarWarpper, mToolbar, mStatusBar)
        RxView.clicks(mBackBtn).throttleFirst(200, TimeUnit
                .MILLISECONDS).subscribe {
            back()
        }

        RxView.clicks(mViewBtn).throttleFirst(200, TimeUnit
                .MILLISECONDS).subscribe {
            finish()
        }
        var questionInfos: ArrayList<QuestionInfoWrapper.QuestionInfo>? = intent.getParcelableArrayListExtra("questionInfos")
        if (questionInfos == null) {
            SimpleCacheUtils.readCache(this, IntelligentQuestionsActivity.getInstance()?.getResultKey()
                    ?: "error",
                    object :
                            SimpleCacheUtils
                            .CacheRunnable
                            () {
                        override fun run() {
                            questionInfos = JSON.parseObject<ArrayList<QuestionInfoWrapper.QuestionInfo>>(json, object :
                                    TypeReference<ArrayList<QuestionInfoWrapper
                                    .QuestionInfo>>
                                    () {}.type)
                            this@IntelligentResultActivity.runOnUiThread {
                                showInfo(questionInfos!!)
                            }
                        }
                    })
        } else {
            showInfo(questionInfos!!)
        }
    }

    private fun showInfo(questionInfos: ArrayList<QuestionInfoWrapper.QuestionInfo>) {
        adapter = IntelligentResultAdapter(questionInfos)
        val gridLayoutManager = GridLayoutManager(this, 5)
        gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val question = adapter.data.get(position)
                return if (question.count > 1) 5 else 1
            }
        })
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = gridLayoutManager
        adapter.setOnItemClickListener { adapter, view, position ->
            if (adapter.getItemViewType(position) == 0) return@setOnItemClickListener
            val questionInfo = adapter.data.get(position) as QuestionInfoWrapper.QuestionInfo
            IntelligentQuestionsActivity.getInstance()?.next(questionInfo.actIndex, questionInfo.frgIndex)
            finish()
        }
    }

    private fun back() {
        finish()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD, tags = arrayOf(Tag(Constant.RESULT_IN)))
    fun showResult(tag: String) {
        mRightTextView.text = fromHtml("对<font color='#6ec82d'> ${adapter.rightCount} </font>题")
        mErrorTextView.text = fromHtml("错<font color='#ee5757'> ${adapter.errorCount}  </font>题")
    }

    override fun getLayoutId() = R.layout.intelligent_activity_result

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}