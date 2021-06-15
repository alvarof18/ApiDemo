package com.alvaro.apidemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvaro.apidemo.adapters.DogsAdapter
import com.alvaro.apidemo.databinding.ActivityMainBinding
import com.alvaro.apidemo.interfaces.APIServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding_Main: ActivityMainBinding

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private var dogImages = mutableListOf<String>()
    lateinit var dogsAdapter:DogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_Main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding_Main.root)
        initRecyclerView()
        binding_Main.svDogs.setOnQueryTextListener(this)
      }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            searchByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit()
                .create(APIServices::class.java)
                .getDogsByBreeds("$query/images")
            val puppies = call.body()
            //Mostrar Mensjae en UI principal
            runOnUiThread {
                if (call.isSuccessful) {
                    // Show RecyclerView
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    dogsAdapter.notifyDataSetChanged()
                } else {
                    // Show Error
                   showErrorDialog()
                }

            }

        }

    }
     private fun showErrorDialog(){
        Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

    }

    private fun initRecyclerView(){
        dogsAdapter = DogsAdapter(dogImages)
        binding_Main.rvDogs.layoutManager = LinearLayoutManager(this)
        binding_Main.rvDogs.adapter = dogsAdapter
    }



}