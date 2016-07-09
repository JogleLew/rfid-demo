package GUI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Frame;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Music extends Frame {

	public Music(String filePath) {
		try {
			URL cb;
			File f = new File(filePath);
			cb = f.toURL();
			AudioClip aau;
			aau = Applet.newAudioClip(cb);
			aau.play();// 循环播放 aau.play() 单曲 aau.stop()停止播放

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}