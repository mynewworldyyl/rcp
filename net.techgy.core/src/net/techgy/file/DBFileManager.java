package net.techgy.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.idgenerator.CacheBaseIDManager;

@Component
public class DBFileManager {

	@Autowired
	private DbFileDao fileDao;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	public String processFiles(List<FileItem> items) {
		if(items == null || items.isEmpty()) {
			return Utils.getInstance().getResponse(null, false, null);
		}
		
		Map<String,String> nameToIds = new HashMap<String,String>();
		for(FileItem i : items) {
			DbFile f = new DbFile();
			f.setId(generator.getStringId(DbFile.class));
			f.setClient(UserContext.getCurrentUser().getLoginClient());
			f.setContentType(i.getContentType());
			f.setName(i.getName());
			f.setOwner(UserContext.getAccount());
			this.fileDao.save(f);
			nameToIds.put(f.getName(), f.getId());
		}
		
		String resp = Utils.getInstance().getResponse(nameToIds, true, null);
		return resp;
		
	}
	
	
}
