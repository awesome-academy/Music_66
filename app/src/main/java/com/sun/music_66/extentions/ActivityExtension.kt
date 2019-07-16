package com.sun.music_66.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.ScreenAnimation

fun AppCompatActivity.addFragment(newClass: Class<out BaseFragment>, screenAnimation: ScreenAnimation,
                                  containerViewId: Int, isAddFragment: Boolean, isAddBackStack: Boolean) {
    val tag = newClass.name
    var fragment = supportFragmentManager.findFragmentByTag(tag) as BaseFragment?
    val transaction = supportFragmentManager.beginTransaction()
    if (fragment != null) {
        if (fragment.isVisible) {
            return
        }
        setAnimation(transaction, screenAnimation)
        transaction.show(fragment).commit()
        return
    }
    fragment = newClass.newInstance()
    setAnimation(transaction, screenAnimation)
    if (isAddFragment) {
        transaction.add(containerViewId, fragment, tag)
    } else {
        transaction.replace(containerViewId, fragment, tag)
    }
    if (isAddBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.commit()
}

private fun setAnimation(transaction: FragmentTransaction, screenAnimation: ScreenAnimation) {
    transaction.setCustomAnimations(screenAnimation.enterToRight, screenAnimation.exitToRight,
            screenAnimation.enterToLeft, screenAnimation.exitToLeft)
}