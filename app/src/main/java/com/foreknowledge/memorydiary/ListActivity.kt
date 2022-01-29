package com.foreknowledge.memorydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.memorydiary.RequestCode.PERMISSION_REQUEST_CODE
import com.pedro.library.AutoPermissions
import com.pedro.library.AutoPermissionsListener
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity() {
    private val context = this@ListActivity


    private lateinit var memoAdapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        memo_list.layoutManager = linearLayoutManager
        memo_list.setHasFixedSize(true)

        btn_create_memo.setOnClickListener { switchTo(context, CreateMemoActivity::class.java) }
        btn_cancel2.setOnClickListener { goBack() }
        }
    override fun onBackPressed() {
        super.onBackPressed()
        goBack()
    }

    private fun goBack() {
        finish()
    }

    override fun onResume() {
        super.onResume()

        val memos = MemoDbTable(this).readAllMemo()
        if (memos.isEmpty())
            memo_notice.visibility = View.VISIBLE
        else
            memo_notice.visibility = View.INVISIBLE

        memoAdapter = MemoListAdapter(memos, object: ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val extras = Bundle()
                extras.putLong(KeyName.MEMO_ID, memoAdapter.getItem(position).id)

                switchTo(context, DetailMemoActivity::class.java, extras)
            }
        })

        memo_list.adapter = memoAdapter
    }


}
