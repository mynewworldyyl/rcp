package net.techgy.ui.core.files;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.techgy.file.DBFileManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.osgiservice.impl.SpringContext;

@SuppressWarnings("serial")
public class CmtyFileUploadServlet extends HttpServlet {

	  public static final String CONTENT_TYPE_HTML = "text/html";
	  public static final String CONTENT_TYPE_JAVASCRIPT = "text/javascript";
	  public static final String CONTENT_TYPE_JSON = "application/json"; // RFC 4627

	  public final static String CHARSET_UTF_8 = "UTF-8";
	  public static final String METHOD_GET = "GET";
	  public static final String METHOD_POST = "POST";
	  
	private ServletFileUpload upload = null;

	public CmtyFileUploadServlet() {
		// 为基于硬盘文件的项目集创建一个工厂
		FileItemFactory factory = new DiskFileItemFactory();
		// 创建一个新的文件上传处理器
		upload = new ServletFileUpload(factory);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 解析请求
		String resp = "";
		String accountName = request.getParameter(UIConstants.REQ_USER_ID);
		String clientId = request.getParameter(UIConstants.REQ_USER_CLIENT_ID);
		String localeStr = request.getParameter(UIConstants.REQ_USER_LOCALE);
		UserContext.init(accountName, clientId, localeStr);
		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items == null) {
				 resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
			}else {
				 resp = SpringContext.getContext()
						.getBean(DBFileManager.class).processFiles(items);
			}
		} catch (FileUploadException e) {
			resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
		}
		response.setContentType(CONTENT_TYPE_HTML);
		response.setCharacterEncoding(CHARSET_UTF_8);
		response.addHeader("Cache-Control",
				"max-age=0, no-cache, must-revalidate, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter w = response.getWriter();
		w.write(resp);
		w.flush();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
