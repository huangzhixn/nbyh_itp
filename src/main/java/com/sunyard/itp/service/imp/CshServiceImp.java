package com.sunyard.itp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunyard.itp.entity.CshPara;
import com.sunyard.itp.mapper.CshMapper;
import com.sunyard.itp.service.CshService;

@Service
public class CshServiceImp implements CshService{
	@Autowired
	private CshMapper cshMapper;
	
	@Override
	public void delete() {
		cshMapper.delete();	
	}
	@Override
	public void add(CshPara params) {
		cshMapper.add(params);
	}
	@Override
	public String queryCsh(String mhtOrderNo) {
		return cshMapper.query(mhtOrderNo);
		
		
	}
	
}
