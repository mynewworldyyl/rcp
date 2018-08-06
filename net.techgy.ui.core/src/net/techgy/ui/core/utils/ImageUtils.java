package net.techgy.ui.core.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class ImageUtils {

	private static final ImageUtils instance = new ImageUtils();
	private ImageUtils() {
	}
	
	public static ImageUtils getInstance() {
		return instance;
	}
	
	public ImageData getImageData(InputStream is,int width,int height ) {
		ImageData id = new ImageData(is);
		ImageData sid = id.scaledTo(width, height);
		return sid;
	}
	
	public ImageData getImageData(InputStream is) {
		ImageData id = new ImageData(is);
		return id;
	}
	
	public Image getImage(InputStream is,int width,int height ) {
		ImageData id = this.getImageData(is, width, height);
		return new Image(Display.getCurrent(),id);
	}
	
	public Image getImage(byte[] is,int width,int height ) {
		ImageData id = this.getImageData(new ByteArrayInputStream(is), width, height);
		return new Image(Display.getCurrent(),id);
	}
	
	public Image getImage(String cp,int width,int height ) {
		InputStream is = this.loadStream(cp);
		ImageData id = this.getImageData(is, width, height);
		return new Image(Display.getCurrent(),id);
	}
	
	public Image getImage(Device d,InputStream is,int width,int height ) {
		ImageData id = this.getImageData(is, width, height);
		return new Image(d,id);
	}
	
	public Image getImage(Device d,InputStream is) {
		ImageData id = this.getImageData(is);
		return new Image(d,id);
	}
	
	public Image getImage(InputStream is) {
		ImageData id = this.getImageData(is);
		return new Image(Display.getCurrent(),id);
	}
	
	
	public Image getImage(Device d,String classPathSrc,int width,int height ) {
		InputStream is = this.loadStream(classPathSrc);
		ImageData id = this.getImageData(is, width, height);
		return new Image(d,id);
	}
	
	public Image getImage(Device d,String classPathSrc) {
		InputStream is = this.loadStream(classPathSrc);
		ImageData id = this.getImageData(is);
		return new Image(d,id);
	}
	
	public Image getImage(String classPathSrc) {
		InputStream is = this.loadStream(classPathSrc);
		ImageData id = this.getImageData(is);
		return new Image(Display.getCurrent(),id);
	}
	
	private InputStream loadStream(String cp) {
		return ImageUtils.class.getResourceAsStream(cp);
	}
	
   
}
