package net.techgy.ui.core.actions;


public abstract class AbstractAction implements IAction {

	private String id;
	
	private Object[] objs;
	
    public AbstractAction(String id,Object...objs){
    	this.id = id;
    	this.objs = objs;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
