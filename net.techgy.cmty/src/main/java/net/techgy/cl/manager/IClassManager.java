package net.techgy.cl.manager;

import java.util.List;

import net.techgy.cl.services.ClassVO;

public interface IClassManager {

	boolean createClass(ClassVO attr);
	
	boolean updateClass(ClassVO attr);
	
	boolean delClass(Long id);
	
	ClassVO findClass(Long id);
	
	List<ClassVO> findClass(String namespace);

	boolean createInstanceClass(ClassVO attr);
	
	List<ClassVO> findInsClass(String namespace);
	
	List<ClassVO> findClassByLike(String namespace);
}
