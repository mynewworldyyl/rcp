/*******************************************************************************
 * Copyright (c) 2009, 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package net.techgy.ui.core.utils;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.techgy.ui.core.RWT;

//长沙市芙蓉区杨家山职院街199号 
/**
 * 1. 请各位考生进入“在线报名”操作之前，认真阅读该网站首页的“2014年省测试中心普通话水平开放测试时间安排”。
2.本中心测试时间为每周三（中午12:30开始）和周六（上午8:30开始，9:30之前未达到测试地点者，视为自动弃考）。请选择您希望测试的日期。
3.长沙市区外的考生，可于测试当天提前半小时到省测试中心办公楼三楼综合服务部（305室）办理缴费、摄像、领取准考证等手续。
4. 自测试完成后7个工作日，考生可在“湖南省语委语言文字培训测试中心”（http://www.hnyycs.org/web/）网站查询成绩，
以及到省测试中心综合服务部（305室）领取证书。

 * @author xj
 *
 */
public final class ExampleUtil {

  private static final int DEFAULT_SPACE = 10;

  private static final String DATA_DIR_PROP = "org.eclipse.rap.examples.dataDir";
  private static final String DEFAULT_DATA_DIR = "/data/rapdemo";

  public static File getDataDirectory() {
    return new File( System.getProperty( DATA_DIR_PROP, DEFAULT_DATA_DIR ) );
  }

  public static Composite initPage( String title, Composite parent ) {
    Composite pageComp = new Composite( parent, SWT.NONE );
    pageComp.setLayout( ExampleUtil.createGridLayoutWithoutMargin( 1, false ) );
    Label label = createHeadlineLabel( pageComp, title );
    label.setLayoutData( createHeadlineLayoutData() );
    Composite contentComp = new Composite( pageComp, SWT.NONE );
    contentComp.setLayoutData( ExampleUtil.createFillData() );
    return contentComp;
  }

  public static void createHeading( Composite parent, String text, int horizontalSpan ) {
    Label label = new Label( parent, SWT.NONE );
    label.setText( text );
    label.setData( RWT.CUSTOM_VARIANT, "heading" );
    GridData labelLayoutData = new GridData();
    labelLayoutData.horizontalSpan = horizontalSpan;
    label.setLayoutData( labelLayoutData );
  }

  public static GridLayout createMainLayout( int numColumns ) {
    GridLayout result = new GridLayout( numColumns, true );
    result.marginWidth = 0;
    result.marginHeight = 0;
    result.marginTop = 0;
    result.verticalSpacing = 0;
    result.horizontalSpacing = 60;
    return result;
  }

  public static GridLayout createMainLayout( int numColumns, int horzSpacing ) {
    GridLayout result = new GridLayout( numColumns, true );
    result.marginWidth = 0;
    result.marginHeight = 0;
    result.marginTop = 0;
    result.verticalSpacing = 0;
    result.horizontalSpacing = horzSpacing;
    return result;
  }

  public static GridLayout createGridLayout( int numColumns,
                                             boolean makeColsEqualWidth,
                                             boolean setTopMargin,
                                             boolean setVertSpacing )
  {
    GridLayout result = new GridLayout( numColumns, makeColsEqualWidth );
    result.marginWidth = DEFAULT_SPACE;
    result.marginHeight = 0;
    result.marginBottom = DEFAULT_SPACE;
    result.horizontalSpacing = DEFAULT_SPACE;
    if( setTopMargin ) {
      result.marginTop = DEFAULT_SPACE;
    }
    if( setVertSpacing ) {
      result.verticalSpacing = DEFAULT_SPACE;
    }
    return result;
  }

  public static GridLayout createGridLayoutWithoutMargin( int numColumns,
                                                          boolean makeColsEqualWidth )
  {
    GridLayout result = new GridLayout( numColumns, makeColsEqualWidth );
    result.marginHeight = 0;
    result.marginWidth = 0;
    return result;
  }

  public static RowLayout createRowLayout( int type, boolean setMargin ) {
    RowLayout result = new RowLayout( type );
    result.marginTop = 0;
    result.marginLeft = 0;
    result.marginHeight = 0;
    if( setMargin ) {
      result.marginBottom = DEFAULT_SPACE;
      result.marginWidth = DEFAULT_SPACE;
    } else {
      result.marginBottom = 0;
      result.marginWidth = 0;
    }
    return result;
  }

  public static FillLayout createFillLayout( boolean setMargin ) {
    FillLayout result = new FillLayout();
    if( setMargin ) {
      result.marginWidth = DEFAULT_SPACE;
      result.marginHeight = DEFAULT_SPACE;
    }
    return result;
  }

  public static GridData createHorzFillData() {
    return new GridData( SWT.FILL, SWT.TOP, true, false );
  }

  public static GridData createFillData() {
    return new GridData( SWT.FILL, SWT.FILL, true, true );
  }

  private static Label createHeadlineLabel( Composite parent, String text ) {
    Label label = new Label( parent, SWT.NONE );
    label.setText( text.replace( "&", "&&" ) );
    label.setData( RWT.CUSTOM_VARIANT, "pageHeadline" );
    return label;
  }

  private static GridData createHeadlineLayoutData() {
    GridData layoutData = new GridData();
    layoutData.verticalIndent = 30;
    layoutData.horizontalIndent = DEFAULT_SPACE;
    return layoutData;
  }

  private ExampleUtil() {
    // prevent instantiation
  }

}
