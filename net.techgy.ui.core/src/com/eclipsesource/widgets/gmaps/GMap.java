/*******************************************************************************
 * Copyright (c) 2002, 2012 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package com.eclipsesource.widgets.gmaps;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import com.eclipsesource.widgets.gmaps.internal.HtmlLoader;


public class GMap extends Composite {

  private static final String[] AVAILABLE_TYPES = new String[] {
    "ROADMAP",
    "SATELLITE",
    "HYBRID",
    "TERRAIN"
  };
  public final static int TYPE_ROADMAP = 0;
  public final static int TYPE_SATELLITE = 1;
  public final static int TYPE_HYBRID = 2;
  public final static int TYPE_TERRAIN = 3;

  private final Browser browser;
  private int type = TYPE_ROADMAP;
  private String address = "";
  private LatLng center = new LatLng( 0, 0 );
  private int zoom = 8;
  private boolean loaded = false;
  private final List<MapListener> listeners = new ArrayList<MapListener>();

  public GMap( Composite parent, int style ) {
    super( parent, style );
    super.setLayout( new FillLayout() );
    browser = new Browser( this, SWT.NONE );
    loadMap();
  }

  @Override
  public void setLayout( Layout layout ) {
    checkWidget();
    // prevent setting another layout
  }

  public void setCenter( LatLng center ) {
    checkWidget();
    if( !this.center.equals( center ) && center != null ) {
      this.center = center;
      if( loaded ) {
        eval( "setCenter( [ " + center.toString() + " ] );" );
      }
      fireCenterChanged();
    }
  }

  public LatLng getCenter() {
    checkWidget();
    return center;
  }

  /**
   * Sets the map type.
   *
   * @param type the map type, one of {@link #TYPE_HYBRID}, {@link #TYPE_ROADMAP},
   *          {@link #TYPE_SATELLITE}, or {@link #TYPE_TERRAIN}
   */
  public void setType( int type ) {
    checkWidget();
    if( type < 0 || type > 3 ) {
      throw new IllegalArgumentException( "Illegal map type" );
    }
    this.type = type;
    if( loaded ) {
      eval( "setType( " + createJsMapType() + " )" );
    }
  }

  public int getType() {
    checkWidget();
    return type;
  }

  /**
   * Zoom can be a value between 0 and 20.
   * Not all areas have data for all levels.
   */
  public void setZoom( int zoom ) {
    checkWidget();
    if( zoom < 0 || zoom > 20 ) {
      throw new IllegalArgumentException( "Illegal zoom value" );
    }
    if( zoom != this.zoom ) {
      this.zoom = zoom;
      if( loaded ) {
        eval( "setZoom( " + Integer.toString( zoom ) + " )");
      }
      fireZoomChanged();
    }
  }

  public int getZoom() {
    checkWidget();
    return zoom;
  }

  /**
   * Sets the location of the map to the best result that matching the address.
   * There will be some delay while the geocoder is queried.
   */
  public void gotoAddress( String address ) {
    checkWidget();
    if( loaded && address != null ) {
      this.address = address;
      eval( "gotoAddress( " + createJsAddress() + " )" );
    }
  }

  /**
   * Resolves address of current location (center).
   * Result will be received asynchronously.
   *
   * @see MapListener#addressResolved()
   */
  public void resolveAddress() {
    checkWidget();
    eval( "resolveAddress()" );
  }

  /**
   * Returns the last address given or resolved. Will not be updated
   * automatically as the location changes.
   *
   * @see GMap#gotoAddress(String)
   * @see GMap#resolveAddress()
   * @see MapListener#addressResolved()
   */
  public String getAddress() {
    checkWidget();
    return address;
  }

  /**
   * This adds a draggable marker with a an infowindow to the current center.
   * However, its currently not possible to get the location of the marker
   * should the user move it.
   */
  public void addMarker( String name ) {
    checkWidget();
    eval( "addMarker( \"" + name + "\" )" );
  }

  public void addMapListener( MapListener listener ) {
    listeners.add( listener );
  }

  public void removeMapListener( MapListener listener ) {
    listeners.remove( listener );
  }

  //////////////////////////////////
  // map creation and event-handling

  private void loadMap() {
    HtmlLoader.load( browser, "GMap.html" );
    browser.addProgressListener( new ProgressListener() {
      public void completed( ProgressEvent event ) {
        // Note: Calling execute/eval before the document is loaded wont work.
        loaded = true;
        StringBuffer script = new StringBuffer();
        script.append( "init( " );
        script.append( "[ " + center.toString() + " ], " );
        script.append( zoom + "," );
        script.append( createJsMapType() );
        script.append( ");" );
        try {
          eval( script.toString() );
        } catch( SWTException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        createBrowserFunctions();
      }
      public void changed( ProgressEvent event ) {
      }
    } );
  }

  private void createBrowserFunctions() {
    new BrowserFunction( browser, "onBoundsChanged" ) {
      @Override
      public Object function( Object[] arguments ) {
        syncCenter( ( Double )arguments[ 0 ], ( Double )arguments[ 1 ] );
        syncZoom( ( Double )arguments[ 2 ] );
        return null;
      }
    };
    new BrowserFunction( browser, "onAddressResolved" ) {
      @Override
      public Object function( Object[] arguments ) {
        resolvedAddress( ( String ) arguments[ 0 ] );
        return null;
      }
    };
  }

  private void syncCenter( Double latitude, Double longitude ) {
    LatLng newCenter = new LatLng( latitude.doubleValue(), longitude.doubleValue() );
    if( !center.equals( newCenter ) ) {
      center = newCenter;
      fireCenterChanged();
    }
  }

  private void syncZoom( Double zoom ) {
    int newZoom = zoom.intValue();
    if( newZoom != this.zoom ) {
      this.zoom = newZoom;
      fireZoomChanged();
    }
  }

  private void resolvedAddress( String string ) {
    // TODO : - Failed or obsolete results are handled neither here nor in js.
    //        - Multiple results are ignored.
    //        - Calling the GeoCoder from client is somewhat unnecessary, could
    //          be done directly from java using Google Maps Web Services.
    address = string;
    fireAddressResolved();
  }

  /////////
  // Helper

  private String createJsMapType() {
    return '"' + AVAILABLE_TYPES[ type ] + '"';
  }

  private String createJsAddress() {
    return "\"" + address + "\"";
  }

  private void fireCenterChanged() {
    for( MapListener listener : listeners ) {
      listener.centerChanged();
    }
  }

  private void fireZoomChanged() {
    for( MapListener listener : listeners ) {
      listener.zoomChanged();
    }
  }

  private void fireAddressResolved() {
    for( MapListener listener : listeners ) {
      listener.addressResolved();
    }
  }

  private void eval( String script ) {
    try {
      browser.evaluate( script );
    } catch( IllegalStateException e ) {
      // user probably clicked too fast, let him try again
      // TODO: In the long run should be replaced by BrowserUtil.evalute somehow,
      // as it is JEE Mode compatible and may support queued scripts in the future. 
    }
  }

}
