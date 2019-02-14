package com.example.android.musicplayerdemo.activities

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.musicplayerdemo.MainViewModel
import com.example.android.musicplayerdemo.R
import com.example.android.musicplayerdemo.stateMachine.Action.*
import com.example.android.musicplayerdemo.stateMachine.PlayerStateMachine
import com.example.android.musicplayerdemo.stateMachine.states.IdleState
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    companion object {
        const val TAG = "MainActivity"
        const val MEDIA_RES_1 = R.raw.funky_town
        const val MEDIA_RES_2 = R.raw.the_man_who
    }

    var playlist: MutableList<Int> = ArrayList()
    private var mUserIsSeeking = false
    val playerSM: PlayerStateMachine = PlayerStateMachine(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSeekbar()

        playlist.add(MEDIA_RES_1)
        playlist.add(MEDIA_RES_2)

        button_previous.setOnClickListener(this)
        button_next.setOnClickListener(this)
        button_reset.setOnClickListener(this)
        button_pause.setOnClickListener(this)
        button_play.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        playerSM.setPlaylist(playlist,Pause())

    }

//    override fun onStop() {
//        super.onStop()
//        if (isChangingConfigurations && mPlayerAdapter.isPlaying()) {
//
//        } else {
//            mPlayerAdapter.release()
//
//        }
//    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_play -> playerSM.performAction(Play())
            R.id.button_pause -> playerSM.performAction(Pause())
            R.id.button_next -> playerSM.performAction(Next())
            R.id.button_previous -> playerSM.performAction(Prev())
            R.id.button_reset -> playerSM.performAction(Stop())
        }
    }


    //region Initializing elements


    private fun initializeSeekbar() {
        seekbar_audio.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                var userSelectedPosition = 0

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    mUserIsSeeking = true
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        userSelectedPosition = progress
                    }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mUserIsSeeking = false
                    //mPlayerAdapter.seekTo(userSelectedPosition)
                }
            })
    }
    //endregion


    //region PlaybackListener

//    inner class PlaybackListener : PlaybackInfoListener {
//
//        override fun onDurationChanged(duration: Int) {
//            seekbar_audio.max = duration
//            Log.d(TAG, String.format("setPlaybackDuration: setMax(%d)", duration))
//        }
//
//        override fun onPositionChanged(position: Int) {
//            if (!mUserIsSeeking) {
//                seekbar_audio.setProgress(position, true)
//                Log.d(TAG, String.format("setPlaybackPosition: setProgress(%d)", position))
//            }
//        }
//
//        override fun onStateChanged(state: State) {
//            onLogUpdated(String.format("onStateChanged(%s)", state))
//        }
//
//        override fun onPlaybackCompleted() {}
//
//        override fun onLogUpdated(formattedMessage: String) {
//            if (text_debug != null) {
//                text_debug.append(formattedMessage)
//                text_debug.append("\n")
//                // Moves the scrollContainer focus to the end
//                scroll_container.post { scroll_container.fullScroll(ScrollView.FOCUS_DOWN) }
//            }
//        }
//    }
//    //endregion
}
