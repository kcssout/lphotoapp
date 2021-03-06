//package com.example.myphotoapp.DogView
//
//import android.Manifest
//import android.app.AlertDialog
//import android.app.SearchManager
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.util.Log
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.SearchView
//import androidx.core.app.ActivityCompat
//import androidx.core.view.GravityCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.DefaultItemAnimator
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myphotoapp.Adapter.subRvAdapter
//import com.example.myphotoapp.R
//import com.example.myphotoapp.DB.DB.Dog
//import com.example.myphotoapp.DogView.ViewModel.DogViewModel
//import com.example.myphotoapp.Logger.Logf
//import com.example.myphotoapp.mainApplication
//import com.google.firebase.BuildConfig
//import kotlinx.android.synthetic.main.activity_dog.*
//import kotlinx.android.synthetic.main.fragment_main.*
//import kotlinx.android.synthetic.main.main_toolbar.*
//import java.util.ArrayList
//
//class DogActivity : AppCompatActivity() {
//
//    val TAG : String = DogActivity.javaClass.simpleName
//    var itemlist: MutableList<Dog> = mutableListOf()
//    var SearchView: SearchView? = null
//    var mAdapter: subRvAdapter? = null
//    var INT_ID_OF_YOUR_REQUEST : Int = 101
//    private lateinit var dogViewModel: DogViewModel
//
//    private val permissionss = mutableListOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA)
//    private val MULTIPLE_PERMISSIONS = 101
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dog)
//
//        val toolbar = toolbar
//        val addbtn = addbtn
//
//        setSupportActionBar(toolbar)
//        val ab = supportActionBar!!
//        ab.setDisplayShowTitleEnabled(false)
//        ab.setDisplayHomeAsUpEnabled(true)
//        ab.setHomeAsUpIndicator(R.drawable.baseline_menu_black_18dp)
//
//
//        mAdapter = subRvAdapter(this, itemlist as ArrayList<Dog>, { dog -> deleteDialog(dog, 1) }, { dog -> deleteDialog(dog, 2) })
//        mRecyclerView.adapter = mAdapter            //mRecyclerView 아이디 가져와서 연결
//
//
//        val lm = LinearLayoutManager(this)  //아래는 이전 recylcerview랑 같음
//        mRecyclerView.layoutManager = lm;
//        mRecyclerView.itemAnimator = DefaultItemAnimator()
//        mRecyclerView.setHasFixedSize(true)
//
//        addbtn.setOnClickListener {
//            val i = Intent(this, DogAddActivity::class.java)
//            startActivity(i)
//        }
//
//
//        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
//
//        dogViewModel.getAll().observe(this, Observer<List<Dog>> {
//            //update UI
//            dog-> mAdapter!!.setDogsData(dog)
//            Logf.v("dogViewModel", "test")
//        })
//    }
//
//    private fun deleteDialog(dogs : Dog, type : Int){
//        var builder = AlertDialog.Builder(this)
//
//        when(type){
//            1 ->{
//                builder.setMessage("선택 내용을 삭제할까요?")
//                        .setNegativeButton(" 아뇨"){
//                            _,_->
//                        }
//                        .setPositiveButton("네"){_,_->
//                            dogViewModel.delete(dogs)
//                        }
//            }
//            2 ->{
//                builder.setMessage("전체 삭제할까요?")
//                        .setNegativeButton(" 아뇨"){
//                            _,_->
//                        }
//                        .setPositiveButton("네"){_,_->
//                            dogViewModel.deleteAll(dogs)
//                        }
//            }
//        }
//        builder.show()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        if (id == R.id.menu_action_search) {
//            return true;
//        } else if (id == android.R.id.home) {
//
//            //메뉴
//
////            main_drawer_layout.openDrawer((GravityCompat.START)) // 네비게이션레이아웃
////            finish();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_search, menu)
//
//        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        SearchView = menu.findItem(R.id.menu_action_search).actionView as SearchView
//
//        SearchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        SearchView!!.maxWidth = Integer.MAX_VALUE
//        SearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                mAdapter!!.filter.filter(query)
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                mAdapter!!.filter.filter(query)
//                return false
//            }
//        })
//
//        return true
//    }
//
//
//
//    override fun onDestroy() {
//        DogDB.destroyInsatance()
//        super.onDestroy()
//    }
//
//
//    companion object{
//        private var instance : DogActivity? = null
//
//        @JvmStatic
//        fun getInstance() : DogActivity =
//                instance
//                        ?: synchronized(this){
//                    instance
//                            ?: DogActivity().also { instance = it }
//                }
//    }
//
//    private fun checkPermissions() {
//        var result: Int
//        val chkpermission: MutableList<String> = ArrayList()
//        for (pm in permissionss) {
//            result = mainApplication.ActivityReference.getContext().checkSelfPermission(pm)
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                chkpermission.add(pm) // deny
//            }
//        }
//
//        if (!chkpermission.isEmpty()) {
//            Log.d(TAG, chkpermission.toString())
//
//            ActivityCompat.requestPermissions(this,chkpermission.toTypedArray(), MULTIPLE_PERMISSIONS)
//
//
//        }
//
//        if(chkpermission.size>0){
//        }
//
//    }
//
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        Log.d(TAG, "Dsjkflsdjklfjksldfjsdklf")
//
//        if (requestCode == MULTIPLE_PERMISSIONS) {
//            //안드로이드 마시멜로 이후 퍼미션 체크.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                for (i in grantResults.indices) {
//                    Log.d(TAG, "count "+ grantResults.size)
//
//                    if (grantResults[i] == 0) {
//                        Log.d(TAG, " ok i " + i + " // "+ grantResults[i])
//                        if (grantResults.size == i + 1) {
//                            Log.d(TAG, " start activity i " + i + " // "+ grantResults[i])
//                        }
//                    } else {
//                        // 거부한 이력이 있으면 true를 반환한다.
//                        if (shouldShowRequestPermissionRationale(permissions[i])) {
//                            requestPermissions(permissions, MULTIPLE_PERMISSIONS)
//                        } else {
//                            AlertDialog.Builder(this)
//
//                                    .setTitle("다시보지않기 클릭.")
//                                    .setMessage(permissions[i] + " 권한이 거부되었습니다 설정에서 직접 권한을 허용 해주세요.")
//                                    .setNeutralButton("설정") { dialog, which ->
//                                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                        intent.data = Uri.fromParts("package",
//                                                BuildConfig.APPLICATION_ID, null)
//                                        startActivity(intent)
//                                        Toast.makeText(applicationContext, "권한 설정 후 다시 실행 해주세요.", Toast.LENGTH_SHORT).show()
//                                        finish()
//                                    }
//                                    .setPositiveButton("확인") { dialog, which ->
//                                        Toast.makeText(applicationContext, "권한 설정을 하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT)
//                                                .show()
//                                        finish()
//                                    }
//                                    .setCancelable(false)
//                                    .create().show()
//                        }// shouldShowRequestPermissionRationale /else
//                    } // 권한 거절
//                } // for end
//            }//Build.VERSION.SDK_INT  end
//        }//requestCode  end
//    }
//
//}
//
