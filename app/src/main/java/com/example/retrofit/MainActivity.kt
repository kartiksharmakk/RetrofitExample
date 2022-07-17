package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofit.databinding.ActivityMainBinding
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        retService = RetrofitInstance.getRetrofitInstance()
            .create(AlbumService::class.java)

        //getRequestbyQueryParameters()
        //getRequestbyPathParameters()
        postRequestUploadAlbum()


    }
    private fun getRequestbyQueryParameters(){
        val responseLiveData:LiveData<Response<Albums>>
                = liveData {
            val response=retService.getAlbums(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList=it.body()?.listIterator()
            if(albumsList!=null){
                while (albumsList.hasNext())
                {
                    val albumItem=albumsList.next()
                    //Log.i("MyTag",albumItem.title)
                    val result=" "+ " Album Title : ${albumItem.title}"+
                            "\n"+" Album ID : ${albumItem.id}"+
                            "\n"+" Album UserID : ${albumItem.userId}"+
                            "\n\n\n"
                    binding.textView.append(result)

                }
            }
        })
    }
    private fun getRequestbyPathParameters(){
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getIdAlbums(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })

    }

    private fun postRequestUploadAlbum(){

        val album=AlbumsItem(69,"My STUDIES",420)
        val postResponse : LiveData<Response<AlbumsItem>> = liveData {
            val response=retService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            val recievedAlbumsItem=it.body()
            val result=" "+ " Album Title : ${recievedAlbumsItem?.title}"+
                    "\n"+" Album ID : ${recievedAlbumsItem?.id}"+
                    "\n"+" Album UserID : ${recievedAlbumsItem?.userId}"+
                    "\n\n\n"
            binding.textView.append(result)
        })
    }

}