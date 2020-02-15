package com.example.absolutesport.ui

import com.example.absolutesport.network.Sport

interface ILandingMvp {
    interface View{
        fun displayScreen()
        fun showLoading()
        fun dismissLoading()
    }
    interface Presenter{
        fun getSports():ArrayList<Sport>
    }
}