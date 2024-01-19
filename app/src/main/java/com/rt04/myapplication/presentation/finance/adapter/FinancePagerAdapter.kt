package com.rt04.myapplication.presentation.finance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rt04.myapplication.R
import com.rt04.myapplication.presentation.finance.pemasukan.IncomeFragment
import com.rt04.myapplication.presentation.finance.pengeluaran.SpendingFragment

class FinancePagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val TAB_TITLES = intArrayOf(
        R.string.pemasukan,
        R.string.pengeluaran
    )

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @SuppressLint("ResourceType")
    @StringRes
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = IncomeFragment()
            1 -> fragment = SpendingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES[position])
    }

}