package com.manubla.taskmanager.views.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.manubla.taskmanager.R
import com.manubla.taskmanager.data.Action
import com.manubla.taskmanager.views.adapters.ViewPagerAdapter
import com.manubla.taskmanager.views.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BaseFragment.OnFragmentInteractionListener {

    private val addActionRequestCode = 1001
    private val actions : ArrayList<Action> = arrayListOf()
    private lateinit var prevMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment.newInstance()
        val todoFragment = TodoFragment.newInstance(actions)
        val categoriesFragment = CategoriesFragment.newInstance()
        val profileFragment = ProfileFragment.newInstance()

        viewpager.apply{
            offscreenPageLimit = POSITION_PROFILE
            adapter = ViewPagerAdapter(supportFragmentManager).apply {
                addFragment(homeFragment)
                addFragment(todoFragment)
                addFragment(categoriesFragment)
                addFragment(profileFragment)
            }
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
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
                R.id.navigation_home -> viewpager.currentItem = POSITION_HOME
                R.id.navigation_todos -> viewpager.currentItem = POSITION_TODOS
                R.id.navigation_categories -> viewpager.currentItem = POSITION_CATEGORIES
                R.id.navigation_profile -> viewpager.currentItem = POSITION_PROFILE
            }
            true
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem?) = item?.let {
//        when (it.itemId) {
//            R.id.action_add -> {
//                startActivityForResult(
//                    Intent(this, AddActivity::class.java)
//                    , addActionRequestCode
//                )
//            }
//        }
//        true
//    } ?: super.onOptionsItemSelected(item)
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            addActionRequestCode -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    data?.let {
//                        addActionToTodoList(it.getParcelableExtra(resultInput))
//                    }
//                }
//            }
//        }
//    }
//
//    private fun addActionToTodoList(action: Action) {
//        actions.add(action)
//        updateCurrentFragment()
//    }
//
//    private fun updateCurrentFragment() {
//        supportFragmentManager.findFragmentByTag(TodoFragment.todoFragmentTag).also {
//            it?.let { fragment ->
//                if (fragment.isVisible && fragment is TodoFragment) {
//                    fragment.updateAdapterData(actions)
//                }
//            }
//        }
//    }


    override fun onFragmentInteraction(input: Action) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val POSITION_HOME       = 0
        private const val POSITION_TODOS      = 1
        private const val POSITION_CATEGORIES = 2
        private const val POSITION_PROFILE    = 3
        const val resultInput = "String:Input"
    }
}
