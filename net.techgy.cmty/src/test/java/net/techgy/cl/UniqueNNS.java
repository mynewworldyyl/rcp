package net.techgy.cl;

import javax.persistence.Column;

import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;

public class UniqueNNS {

	@Column(name="namespace")
	@UIElement
	@UIQuery(displayName="Name Space", seq=2)
	private String namespace = "default";
	
	@UIElement
	@Column(nullable=false)
	private String name="";
}
