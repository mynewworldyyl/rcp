package net.techgy.cmty.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

public class ResourceLoaderUtil {

	  private static final ClassLoader CLASSLOADER = ResourceLoaderUtil.class.getClassLoader();

	  public static String readTextContent( String resource ) {
	    try {
	      return readTextContentChecked( resource );
	    } catch( IOException e ) {
	      throw new IllegalArgumentException( "Failed to read: " + resource );
	    }
	  }

	  private static String readTextContentChecked( String resource ) throws IOException {
	    InputStream stream = CLASSLOADER.getResourceAsStream( resource );
	    if( stream == null ) {
	      throw new IllegalArgumentException( "Not found: " + resource );
	    }
	    try {
	      BufferedReader reader = new BufferedReader( new InputStreamReader( stream, "UTF-8" ) );
	      return readLines( reader );
	    } finally {
	      stream.close();
	    }
	  }

	  private static String readLines( BufferedReader reader ) throws IOException {
	    StringBuilder builder = new StringBuilder();
	    String line = reader.readLine();
	    while( line != null ) {
	      builder.append( line );
	      builder.append( '\n' );
	      line = reader.readLine();
	    }
	    return builder.toString();
	  }
      
	  public static Image getImage(String pathOfIcon) {
          Bundle bundle = Platform.getBundle(CmtyUIActivator.PLUGIN_ID);
          URL url = bundle.getEntry("icons/"+pathOfIcon);
          try {
                 url = Platform.asLocalURL(url);
          } catch (Exception e) {
                 //PwdgatePlugin.log("get root path", e);
        	  e.printStackTrace();
          }
          ImageDescriptor des = ImageDescriptor.createFromURL(url);
          return des.createImage();
   }
	  
}
