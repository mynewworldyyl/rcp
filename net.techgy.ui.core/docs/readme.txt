web访问地址构成：
http://IP:PORT/contextName/ConfigurationName/entryPointName

1. contextName 或者 contextPath, WEB部署的影射名，对应Tomcat的webapp下面的文件夹名称，或者
   context下面的path名称；
       此值在eclipse插件启动下对应配置的最顶端的Name字段，其在文件系统下面的目录为：
   D:\tools\eclipse\eclipse-jee-luna-R-win32_RAP\ws\.metadata\.plugins\org.eclipse.pde.core\demo

2. ConfigurationName： ApplicationConfiguration注册时加入的contextName值，如果没有指定
       此值，默认为'/'. 配置contextName时的名称前面不能加"/",此与entryPointName不同；
       此值可以在Activator，web.xml, Service-Component文件中指定。
   Activitor的start方法中：
   
      Configuration configuration = new Configuration();
	  Dictionary<String, Object> properties = new Hashtable<String, Object>();
	  properties.put( "contextName", "cmty" );
	  registration = bundleContext.registerService( ApplicationConfiguration.class.getName(), 
	                                                configuration, 
	                                                properties );
    Service-Component：
    <scr:component xmlns:scr="http://www.osgi.org/xmlns/scr/v1.1.0" 
	name="net.techgy.ui.core.CmtyCoreConfiguration">
	   <implementation class="net.techgy.ui.core.CmtyCoreConfiguration"/>
	   <service>
	      <provide interface="org.eclipse.rap.rwt.application.ApplicationConfiguration"/>
	   </service>
	   <property name="contextName" type="String" value="cmty"/>
	</scr:component>   
	
	web.xml：
	 <context-param>
    <param-name>org.eclipse.rap.applicationConfiguration</param-name>
    <param-value>com.digitnexus.tabris.demo.Configuration</param-value>
  </context-param>
  
  
  <context-param>
    <param-name>contextName</param-name>
    <param-value>app</param-value>
  </context-param>
 
  <listener>
    <listener-class>org.eclipse.rap.rwt.engine.RWTServletContextListener</listener-class>
  </listener>
 
  <servlet>
    <servlet-name>rwtServlet</servlet-name>
    <servlet-class>org.eclipse.rap.rwt.engine.RWTServlet</servlet-class>
  </servlet>
 
  <servlet-mapping>
    <servlet-name>rwtServlet</servlet-name>
    <url-pattern>/app</url-pattern>
  </servlet-mapping>                                      
       
       
3. entryPointName： 在ApplicationConfiguration实现中注册EntryPoint时使用的名称。注意前
      面加"/"