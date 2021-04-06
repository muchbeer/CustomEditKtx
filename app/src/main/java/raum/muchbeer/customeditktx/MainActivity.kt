package raum.muchbeer.customeditktx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import raum.muchbeer.customeditktx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  //  private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setQueryTextChangeListener(object : EditTextSearchCustomView.QueryTextListener{
            override fun onQueryTextSubmit(query: String?) {
                Log.d("MainActivity", "Clicked the back button ready to be set back with value")
            }

            override fun onQueryTextChange(newText: String?) {
               Log.d("MainActivity", "value is ${newText}")
            }

        })
    }
}