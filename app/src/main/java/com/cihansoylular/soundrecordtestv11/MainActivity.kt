package com.cihansoylular.soundrecordtestv11

import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.*
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import java.io.File
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {
    lateinit var recorder: MediaRecorder
    lateinit var player: MediaPlayer
    lateinit var file: File
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var saveButton: Button
    lateinit var tv1: TextView
    lateinit var timeText : TextView
    lateinit var topicText: EditText
   // var PlainText: topicText?  = null
    private var mdatabase:FirebaseDatabase?=null
    private var mRef : DatabaseReference?=null
    var handler: Handler = Handler()
    var runnbale: Runnable = Runnable {  }
    var cihan: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val intent = intent
        val received: String = intent.getStringExtra("input")
        nameText.text ="Hello "+ received

        //firebase database e baglaniyoruz
        mdatabase= FirebaseDatabase.getInstance()
        //database içerisindeki hardware adli child e ulaşıyouz
        mRef = mdatabase!!.getReference("hardware")

         fun sendMessage(view: View){

            mRef!!.child("hardware").child("topics").setValue(topicText.text.toString())
        }
        mRef = mdatabase!!.getReference("topics")

       // mRef!!.child("hardware").child("topics")


        mRef!!.child("hardware").child("topics").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var value:String = p0!!.getValue() as String
                topicText.setText(value)
            }

        })

        tv1 = findViewById (R.id.tv1) as TextView
        button1 = findViewById (R.id.button1) as Button
        button2 = findViewById (R.id.button2) as Button
        button3 = findViewById (R.id.button3) as Button
        topicText =findViewById(R.id.topicText)
        saveButton=findViewById(R.id.saveButton)

        timeText = findViewById(R.id.timeText) as TextView



        var button2clk: View.OnClickListener = View.OnClickListener{
            cihan?.cancel()
            recorder.stop ()
            recorder.release ()
            player = MediaPlayer ()

            player.setOnCompletionListener (this)



            try {
                player.setDataSource(file.absolutePath)
            } catch (e: IOException) {
            }

            try {
                player.prepare ()
            } catch (e: IOException) {
            }


            button1.setEnabled (true)
            button2.setEnabled (false)
            button3.setEnabled (true)
            tv1.text = "Ready to play"

            timeText.text ="Done!"
            //    timeText.visibility =View.INVISIBLE
        }
        button1.setOnClickListener {
            if (topicText.text.toString().length == 0){
                button1.isClickable=false
                Toast.makeText(this, "The topic must be Entered!!",Toast.LENGTH_LONG).show()
            }

            recorder = MediaRecorder()
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            val path = File (Environment.getExternalStorageDirectory().getPath())



            try {
                file = File.createTempFile ("temporary", ".mp3", path)
            } catch (e: IOException) {
            }

            recorder.setOutputFile(file.absolutePath)
            try {
                recorder.prepare ()
            } catch (e: IOException) {
            }

            recorder.start()
            tv1.text = "Recording"
            button1.setEnabled(false)
            button2.setEnabled(true)
            button3.setEnabled(false)

            cihan = object : CountDownTimer(60000,1000) {
                override fun onFinish(){
                    timeText.text = "Time's Finish!"
                    button2clk.onClick(button2)

                }

                override fun onTick(p0: Long) {
                    timeText.text = "Time: "+ p0/1000 +" Sec"
                }
            }.start()

            }



        button2.setOnClickListener(button2clk)


        button3.setOnClickListener {
            player.start();
            button1.setEnabled (false);
            button2.setEnabled (false);
            button3.setEnabled (false);
            tv1.setText ("Playing");
        }
    }



    override fun onCompletion (mp: MediaPlayer) {
        button1.setEnabled (true)
        button2.setEnabled (false)
        button3.setEnabled (true)
        tv1.setText ("Ready")
    }
}