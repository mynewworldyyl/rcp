package com.digitnexus.core.uidef.menu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.menu.IMenuManager;
import com.digitnexus.base.uidef.menu.Menu;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.auth.IAuthorization;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.vo.ReportVo;
import com.digitnexus.core.vo.dept.AccountVo;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.core.vo.dept.EmployeeVo;
import com.digitnexus.core.vo.dept.GroupVo;
import com.digitnexus.core.vo.map.MapVo;
import com.digitnexus.core.vo.masterdata.ConstantVo;
import com.digitnexus.core.vo.masterdata.ContractVo;
import com.digitnexus.core.vo.masterdata.MaterialTypeVo;
import com.digitnexus.core.vo.masterdata.MaterialVo;
import com.digitnexus.core.vo.masterdata.ProjectVo;
import com.digitnexus.core.vo.masterdata.QualityVo;
import com.digitnexus.core.vo.masterdata.SpecModelVo;
import com.digitnexus.core.vo.masterdata.TaxRateVo;
import com.digitnexus.core.vo.masterdata.VendorTypeVo;
import com.digitnexus.core.vo.masterdata.VendorVo;
import com.google.gson.reflect.TypeToken;

@Component("menuManager")
@Path("menuManager")
public class MenuManager implements IMenuManager {
	
	@Autowired
	private IAuthorization author;
	
	private Map<String,List<Menu>> clientMenusMap = new HashMap<String,List<Menu>>();
	
	public MenuManager(){
		initMenu();
	}
	
	@Override
	@Path("/mainMenus")
	public String getMainMenus() {
		String clientType = UserContext.getCurrentUser().getLoginClient().getTypecode().getTypeCode();
		
		List<Menu> allMenus = clientMenusMap.get(clientType);
		
		List<Menu> subMenuList = null;
		List<Menu> mainMenuList = new ArrayList<Menu>();
		
		for(Menu m : allMenus) {
			subMenuList = new ArrayList<Menu>();
			for(Menu subm : m.getSubMenus()) {
				if(this.author.authorize(subm.getObjType(), ActionType.Query.name())) {
					subMenuList.add(subm);
				}
			}
			
			if(!subMenuList.isEmpty()) {
				Menu mainMenu = null;
				try {
					mainMenu = m.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				mainMenu.getSubMenus().clear();
				mainMenu.getSubMenus().addAll(subMenuList);
				mainMenuList.add(mainMenu);
			}
		}
		
		Response resp = new Response(true);
		String json = JsonUtils.getInstance().toJson(mainMenuList,true);
		resp.setData(json);
		Type type = new TypeToken<List<Menu>>(){}.getType();
		resp.setClassType(type.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
public void initMenu() {
		
		List<Menu> zongBu = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Headquarter, zongBu);
		
		List<Menu> daQu = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Region, daQu);
		
		List<Menu> gongChang = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Factory, gongChang);		
		
		List<Menu> xiangMu = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Project, xiangMu);
		
		List<Menu> gongYinShang = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Vendor, gongYinShang);
		
		List<Menu> admin = new ArrayList<Menu>();
		this.clientMenusMap.put(ClientType.Admin, admin);
		
		Menu mainMenu = new Menu("系统管理",null,null);
		Menu subMenu = new Menu("部门管理",DepartmentVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("职员管理",EmployeeVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("账号管理",AccountVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("权限组管理",GroupVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		xiangMu.add(mainMenu);
		gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		
		mainMenu = new Menu("基础管理",null,null);
		subMenu = new Menu("常量",ConstantVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材质",QualityVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("税率",TaxRateVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("资产类别",MaterialTypeVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("资产",MaterialVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料规格",SpecModelVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		xiangMu.add(mainMenu);
		gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		
		mainMenu = new Menu("供应商信息",null,null);
		subMenu = new Menu("供应商类别",VendorTypeVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("供应商名册",VendorVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		xiangMu.add(mainMenu);
		//gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		mainMenu = new Menu("项目信息",null,null);
		subMenu = new Menu("项目信息维护",ProjectVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		//xiangMu.add(mainMenu);
		gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		
		mainMenu = new Menu("合同管理",null,null);
		subMenu = new Menu("合同信息维护",ContractVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		//xiangMu.add(mainMenu);
		gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		
		mainMenu = new Menu("Map&Report",null,null);
		
		subMenu = new Menu("Google Map",MapVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("Report",ReportVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		
		zongBu.add(mainMenu);
		daQu.add(mainMenu);
		gongChang.add(mainMenu);
		xiangMu.add(mainMenu);
		gongYinShang.add(mainMenu);
		admin.add(mainMenu);
		
		
		/*Menu mainMenu = new Menu("供应商信息",null,null);
		Menu subMenu = new Menu("供应商类别管理","");
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("供应商名册管理","");
		mainMenu.addSubMenu(subMenu);	*/
		/*
		mainMenu = new Menu("项目信息",null,null);
		subMenu = new Menu("项目信息维护","");
		mainMenu.addSubMenu(subMenu);
		menus.add(mainMenu);
		
		mainMenu = new Menu("合同管理",null,null);
		subMenu = new Menu("项目合同","");
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("项目明细","");
		mainMenu.addSubMenu(subMenu);
		menus.add(mainMenu);
		
		mainMenu = new Menu("材料清单",null,null);
		subMenu = new Menu("材料入库单",AssetInRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料退货单",AssetReturnProviderRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料出库单",AssetOutRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料退库单",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		menus.add(mainMenu);
	
		mainMenu = new Menu("材料查询",null,null);
		subMenu = new Menu("材料位置查询",AssetMap.class.getName() );
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料入库查询",AssetInReport.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料在库查询",AssetReturnProviderRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料出库查询",AssetOutRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("快速盘点查询",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("厂内调拨查询",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料查询",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料退货查询",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料退库查询",AssetReturnStoreRequestDemo.class.getName());
		mainMenu.addSubMenu(subMenu);
		menus.add(mainMenu);*/
		
		
		/*mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("公式管理",EmployeeVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料类别",AccountVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材料规格型号",GroupVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("材质",AccountVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		subMenu = new Menu("税率",GroupVo.class.getName());
		mainMenu.addSubMenu(subMenu);
		*/
		//menus.add(mainMenu);
		
	}
}
