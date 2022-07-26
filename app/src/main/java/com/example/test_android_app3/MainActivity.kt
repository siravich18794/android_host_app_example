package com.example.test_android_app3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {
    private  lateinit var button: Button
    private lateinit var flutterEngine: FlutterEngine
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("1234", flutterEngine)

        button = findViewById(R.id.button)
//        textView1 = findViewById(R.id.textView1)
//        textView2 = findViewById(R.id.textView2)
//        textView3 = findViewById(R.id.textView3)
//        textView4 = findViewById(R.id.textView4)
//        textView5 = findViewById(R.id.textView5)

        val channel = "FlutterModule/test"
        val mc = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channel)
        mc.setMethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            if (methodCall.method == "test") {
                // Get values from flutter
                val rawData = methodCall.arguments
                val data = rawData as Map<*, *>
                textView1.text = data["title"].toString()
                textView2.text = data["firstNameTH"].toString()
                textView3.text = data["lastNameTH"].toString()
                textView4.text = data["firstNameENG"].toString()
                textView5.text = data["lastNameENG"].toString()


                //Log.d("test" , data["lastName"].toString())


                // Print data that sent from flutter in flutter
                result.success(
                    "Messages from android : Sending successfully"

                )
                //Accessing data sent from flutter
            } else {
                print("new method came : "+methodCall.method)
            }
        }



        button.setOnClickListener{
            startActivity(
                FlutterActivity
                    .withCachedEngine("1234")
                    .build(this)
            )
        }
    }
}