package net.techgy.cl.manager;

import java.util.List;

import net.techgy.cl.Namespace;
import net.techgy.ui.manager.PName;


public interface INamespaceManager{

	public boolean createNamespace(@PName("ns") Namespace ns);

	public boolean updateNamespace(@PName("ns") Namespace ns);

	public boolean delNamespace(@PName("id") Long id);
	
	public Namespace findNamespace(@PName("id") Long id);

	public Namespace findNamespace(@PName("ns") String namespace);

	public List<Namespace> findNSByLike(@PName("queryStr") String queryStr);
}
