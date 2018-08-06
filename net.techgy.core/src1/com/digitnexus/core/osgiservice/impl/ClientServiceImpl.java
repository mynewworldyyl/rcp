package com.digitnexus.core.osgiservice.impl;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.service.IClientService;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.i18n.I18NUtils;

@Component
public class ClientServiceImpl implements IClientService{

	@Autowired
	private ClientServiceWithTransactionImpl transacationImpl;
	
	@Override
	public String call(String url, String jsonParams) {
		Response resp = null;
		try{
			return transacationImpl.processCall(url, jsonParams);
		}catch(Throwable exce) {
			exce.printStackTrace();
			resp = new Response(false);
			if(exce instanceof CommonException) {
				CommonException cd = (CommonException)exce;
				resp.setMsg(I18NUtils.getInstance().getString(cd.getI18nKey(),cd.getArgs()));
			}else if(exce instanceof InvocationTargetException) {
				InvocationTargetException e = (InvocationTargetException)exce;
				Throwable ex = e.getTargetException();
				if(ex instanceof CommonException) {
					CommonException cd = (CommonException)ex;
					resp.setMsg(I18NUtils.getInstance().getString(cd.getI18nKey(),cd.getArgs()));
				}else {
					resp.setMsg(I18NUtils.getInstance().getString(exce.getMessage()== null ? "NotExpectedException": exce.getMessage()));
				}
			}else {
				resp.setMsg(I18NUtils.getInstance().getString(exce.getMessage()== null ? "NotExpectedException": exce.getMessage()));
			}
			return JsonUtils.getInstance().toJson(resp,false);
		}finally{
			
		}
	}
	
}
