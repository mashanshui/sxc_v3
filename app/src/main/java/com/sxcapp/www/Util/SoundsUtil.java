package com.sxcapp.www.Util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.sxcapp.www.R;

import java.util.HashMap;

/**
 * Created by wenleitao on 2018/5/9.
 */

public class SoundsUtil {


    public static final int KEY_SOUND_OPEN = 1;
    public static final int KEY_SOUND_CLOSE = 2;
    private HashMap<Integer, Integer> soundPoolMap = new HashMap<>();
    private SoundPool mSoundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);

    public SoundsUtil(Context context) {
        soundPoolMap.put(KEY_SOUND_OPEN, mSoundPool.load(context, R.raw.open, 1));
        soundPoolMap.put(KEY_SOUND_CLOSE, mSoundPool.load(context, R.raw.close, 1));
    }

    public void playOpenSound() {
        mSoundPool.play(soundPoolMap.get(KEY_SOUND_OPEN), 1, 1, 0, 0, 1);
    }

    public void playCloseSound() {
        mSoundPool.play(soundPoolMap.get(KEY_SOUND_CLOSE), 1, 1, 0, 0, 1);
    }
}
