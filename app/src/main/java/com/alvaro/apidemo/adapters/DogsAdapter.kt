package com.alvaro.apidemo.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.apidemo.R

class DogsAdapter(private val imagesList:List<String>) : RecyclerView.Adapter<DogViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog,parent,false))
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = imagesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = imagesList.size

}
