//package com.d.jetpack.arch.viewModel
//
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.Observer
//import android.arch.lifecycle.ViewModel
//import android.arch.lifecycle.ViewModelProviders
//import android.arch.persistence.room.*
//import android.arch.persistence.room.OnConflictStrategy.REPLACE
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Path
//import java.util.concurrent.Executor
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Entity
//data class User(@PrimaryKey val id:String, val name:String)
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = REPLACE)
//    fun save(user: User)
//    @Query("SELECT * FROM user WHERE id = :userId")
//    fun load(id: String):LiveData<User>
//}
//
//@Database(entities = arrayOf(User::class), version = 1)
//abstract class MyDatabase : RoomDatabase() {
//
//}
//
//
//class UserProfileViewModel() : ViewModel() {
//    lateinit var user:LiveData<User>
//    lateinit var id: String
//    lateinit var userRepo: UserRepo
//
//    @Inject
//    constructor(userRepo: UserRepo) : this() {
//        this.userRepo = userRepo
//    }
//
//    fun init(id:String) {
//        this.id = id
//    }
//}
//
//class UserProfileFragment() : Fragment() {
//    lateinit var viewModel: UserProfileViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        val id = arguments!!.getString("id")
//
//        // ViewModel is automatically restored when the configuration changes  (for example, user rotating the screen).
//        //  This is the reason why ViewModels should not reference Views *directly*
//        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
//        viewModel.init(id)
//
//        // LiveData 监听数据的变化, 来更新UI
//        // 因为LiveData监听了fragment的生命周期, 所以这里不需要在onStop的时候停止observe, LivaData会自动移除
//        viewModel.user.observe(this, Observer<User> {
//
//        })
//    }
//}
//
//interface WebService {
//    @GET("/users/{user}")
//    fun getUser(@Path("user") userId:String): Call<User>
//}
//
//// principle of separation of concerns
//// 没有让ViewModel直接调用WebService
//@Singleton
//class UserRepo {
//
//    // WebService的初始化 complicate and duplicate the code
//    // each class that needs a Webservice instance would need to know how to construct it with its dependencies
//    // If each class creates a new WebService, it would be very resource heavy.
//    // 1. Dependency Injection - Dagger2
//    // 2. Service Locator
//    lateinit var webService: WebService
//    lateinit var userDao: UserDao
//    lateinit var executor: Executor
//
//    @Inject
//    constructor(webService: WebService, userDao: UserDao, executor: Executor) {
//        this.webService = webService
//        this.userDao = userDao
//        this.executor = executor
//    }
//
//    fun getUser(userId:String):LiveData<User> {
//        refreshUser(userId)
//
//        return userDao.load(userId)
//
////        val data = MutableLiveData<User>()
////        webService.getUser(userId).enqueue(object :Callback<User> {
////            override fun onFailure(call: Call<User>?, t: Throwable?) {
////                t?.printStackTrace()
////            }
////
////            override fun onResponse(call: Call<User>?, response: Response<User>?) {
////                data.value = response!!.body()
////            }
////        })
////
////        return data
//    }
//
//    fun refreshUser(userId: String) {
//        executor.execute {
//            val userExists = true
//            if(!userExists) {
//                val response = webService.getUser(userId).execute()
//                // 数据库在在这里是Single source of truth
////                userDao.save(response.body())
//            }
//        }
//    }
//}
