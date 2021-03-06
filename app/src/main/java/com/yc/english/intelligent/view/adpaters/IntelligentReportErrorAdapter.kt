package com.yc.english.intelligent.view.adpaters

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yc.english.R
import com.yc.english.intelligent.model.domain.ReportErrorInfo
import yc.com.blankj.utilcode.util.SizeUtils

/**
 *
 * Created by wanglin  on 2018/4/19 16:32.
 */
class IntelligentReportErrorAdapter(errors: List<ReportErrorInfo>?) : BaseQuickAdapter<ReportErrorInfo, BaseViewHolder>(R.layout.intelligent_report_error, errors) {
    override fun convert(helper: BaseViewHolder?, item: ReportErrorInfo?) {
        helper!!.setText(R.id.tv_title_number, "" + (helper.layoutPosition + 1))
                .setText(R.id.tv_title, item?.title)
                .setText(R.id.tv_your_answer, item?.user_answer)
                .setText(R.id.tv_reference_answer, item?.answer)
                .setText(R.id.tv_analysis, item?.analysis)
                .setText(R.id.tv_knowledge, item?.knowledge)
        val recyclerView = helper.getView<RecyclerView>(R.id.option_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        recyclerView.adapter = IntelligentReportErrorItemAdapter(item?.options, item?.type)
        recyclerView.addItemDecoration(MyDecoration())

    }

    class MyDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(0, 0, 0, SizeUtils.dp2px(5.0f))
        }
    }
}