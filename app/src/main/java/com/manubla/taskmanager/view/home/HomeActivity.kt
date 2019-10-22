package com.manubla.taskmanager.view.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.extension.gone
import com.manubla.taskmanager.extension.invisible
import com.manubla.taskmanager.extension.visible
import com.manubla.taskmanager.view.home.categories.CategoriesFragment
import com.manubla.taskmanager.view.home.profile.ProfileFragment
import com.manubla.taskmanager.view.home.summary.SummaryFragment
import com.manubla.taskmanager.view.home.todo.TodoFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(),
    BaseFragment.OnFragmentInteractionListener {

    private lateinit var prevMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {

            val homeFragment = SummaryFragment.instance
            val todoFragment = TodoFragment.instance
            val categoriesFragment = CategoriesFragment.instance
            val profileFragment = ProfileFragment.instance

            viewpager.apply {
                offscreenPageLimit =
                    POSITION_PROFILE
                adapter = HomeViewPagerAdapter(supportFragmentManager).apply {
                    addFragment(homeFragment)
                    addFragment(todoFragment)
                    addFragment(categoriesFragment)
                    addFragment(profileFragment)
                }
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        if (::prevMenuItem.isInitialized)
                            prevMenuItem.isChecked = false
                        else
                            navigation.menu.getItem(0).isChecked = false

                        navigation.menu.getItem(position).isChecked = true
                        prevMenuItem = navigation.menu.getItem(position)
                    }
                })
            }

            navigation.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> viewpager.currentItem =
                        POSITION_HOME
                    R.id.navigation_todos -> viewpager.currentItem =
                        POSITION_TODOS
                    R.id.navigation_categories -> viewpager.currentItem =
                        POSITION_CATEGORIES
                    R.id.navigation_profile -> viewpager.currentItem =
                        POSITION_PROFILE
                }
                true
            }

        }

    }


    fun showProgress() {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        progress.apply {
            visible()
            animate().setDuration(shortAnimTime.toLong())
                .alpha(1f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visible()
                    }
                })
        }
    }

    fun hideProgress() {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        progress.apply {
            animate().setDuration(shortAnimTime.toLong())
                .alpha(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        invisible()
                    }
                })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_TODO_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && TodoFragment.instance.isVisible) {
                    data?.let {
                        TodoFragment.instance.onAddTodo(it.getParcelableExtra(resultInput))
                    }
                }
            }
        }
    }



    override fun onFragmentInteraction(input: Parcelable) {
        val action = input as Action
        when (action.actionType) {
            SHOW_PROGRESS_ACTION -> showProgress()
            HIDE_PROGRESS_ACTION -> hideProgress()
        }
    }

    companion object {
        private const val POSITION_HOME       = 0
        private const val POSITION_TODOS      = 1
        private const val POSITION_CATEGORIES = 2
        private const val POSITION_PROFILE    = 3

        const val ADD_TODO_REQUEST_CODE = 1001

        const val SHOW_PROGRESS_ACTION = 2001
        const val HIDE_PROGRESS_ACTION = 2002

        const val resultInput = "String:Input"
    }
}
