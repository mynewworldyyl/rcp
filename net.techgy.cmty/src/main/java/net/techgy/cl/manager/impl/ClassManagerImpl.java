package net.techgy.cl.manager.impl;

import java.util.ArrayList;
import java.util.List;

import net.techgy.cl.ClUtils;
import net.techgy.cl.Class;
import net.techgy.cl.dao.IAttributeDao;
import net.techgy.cl.dao.IClassDao;
import net.techgy.cl.manager.IClassManager;
import net.techgy.cl.services.ClassVO;
import net.techgy.ui.manager.Operation;
import net.techgy.ui.manager.VOManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("ClassManagerImpl1")
@VOManager("classManager")
public class ClassManagerImpl implements IClassManager{
	
	@Autowired
	private IClassDao clsDao;
	
	@Autowired
	private IAttributeDao attrDao;
	
	@Operation(value = "create")
	@Transactional
	@Override
	public boolean createClass(ClassVO vo) {
		Class vc = null;// //ClUtils.getInstance().clsVOToCls(vo,attrDao);
		if(null == vc) {
			return false;
		}
		this.clsDao.saveVariantClass(vc);
		return true;
	}
    
	@Operation(value="update")
	@Transactional
	@Override
	public boolean updateClass(ClassVO vo) {
		Class vc = null;//ClUtils.getInstance().clsVOToCls(vo,attrDao);
		if(null == vc) {
			return false;
		}
		this.clsDao.updateVariantClass(vc);
		return true;
	}

	@Operation(value="remove")
	@Transactional
	@Override
	public boolean delClass(Long id) {
		this.clsDao.delVariantClass(id);
		return true;
	}

	@Operation(value="queryById")
	@Transactional
	@Override
	public ClassVO findClass(Long id) {
		Class vc = null;
		if(null != id && id>0) {
			vc = this.clsDao.findVariantClass(id);
		}
		if(null ==vc) {
			return null;
		}
		ClassVO vo = null;//ClUtils.getInstance().clsToClsVO(vc);
		return vo;
	}

	@Operation(value="queryByNamespace")
	@Transactional
	@Override
	public List<ClassVO> findClass(String namespace) {
		namespace = null ==namespace ? "" : namespace.trim();
		List<ClassVO> clsVos = new ArrayList<ClassVO>();
		List<Class> vcs = this.clsDao.findVariantClass(namespace);
		for(Class cls: vcs) {
			clsVos.add(null/*ClUtils.getInstance().clsToClsVO(cls)*/);
		}
		return clsVos;
	}
	
	@Operation(value="query")
	@Transactional
	@Override
	public List<ClassVO> findClassByLike(String qStr) {
		qStr = qStr == null ? "" : qStr.trim();
		List<ClassVO> clsVos = new ArrayList<ClassVO>();
		List<Class> vcs = this.clsDao.findClassByLike(qStr);
		for(Class cls: vcs) {
			clsVos.add(null/*ClUtils.getInstance().clsToClsVO(cls)*/);
		}
		return clsVos;
	}

	@Transactional
	@Override
	public boolean createInstanceClass(ClassVO vo) {
		Class vc = null;// ClUtils.getInstance().clsVOToCls(vo,attrDao);
		if(null == vc) {
			return false;
		}
		this.clsDao.saveVariantClass(vc);
		return true;
	}

	@Transactional
	@Override
	public List<ClassVO> findInsClass(String namespace) {
		if(null ==namespace || "".equals(namespace.trim())) {
			return null;
		}
		List<ClassVO> clsVos = new ArrayList<ClassVO>();
		List<Class> vcs = this.clsDao.findInsClass(namespace);
		for(Class cls: vcs) {
			clsVos.add(null/*ClUtils.getInstance().clsToClsVO(cls)*/);
		}
		return clsVos;
	}
	
	
}
