package com.ssaapp.wishlist


import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssaapp.wishlist.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity(), WishlistAdapter.OnItemClickListener {
    // Fetch the list of data
    private val items = ItemFetcher.getItems()
    // Create adapter passing in the list of data
    private val wishlistAdapter = WishlistAdapter(items, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Lookup the RecyclerView in activity layout
        val itemRecyclerView = binding.itemRecyclerView
        // Create adapter passing in the list of data
        itemRecyclerView.adapter = wishlistAdapter
        // Set layout manager to position the items
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        // Leveraging ItemClickSupport decorator to handle clicks on items in our recyclerView

        binding.nameEditText.setOnClickListener{
            binding.nameEditText.selectAll()
        }
        binding.urlEditText.setOnClickListener{
            binding.urlEditText.selectAll()
        }
        binding.submitButton.setOnClickListener() {
            val inputName = binding.nameEditText.text.toString()
            val inputUrl = binding.urlEditText.text.toString()
            try {
                val inputPrice = binding.priceEditText.text.toString().toDouble()
                inputName.uppercase()
                "%.2f".format(inputPrice).toDouble()
                items.add(Item(inputName, inputPrice, inputUrl))
                wishlistAdapter.notifyDataSetChanged()
            } catch(e: Exception) {
                Toast.makeText(applicationContext, "Error: Price entered was not valid. Please enter a number.", Toast.LENGTH_SHORT).show()
                binding.priceEditText.text.clear()
            }
        }
        binding.submitButton.setOnLongClickListener {
            true
        }
    }

    override fun onItemClick(position: Int) {
        try {
            val url = items[position].url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Invalid URL for " + items[position].url, Toast.LENGTH_LONG).show()
        }
    }

    //Long click deletes wishlist entry at click position
    override fun onLongItemClick(position: Int) {
        items.removeAt(position)
        wishlistAdapter.notifyDataSetChanged()
    }
}

