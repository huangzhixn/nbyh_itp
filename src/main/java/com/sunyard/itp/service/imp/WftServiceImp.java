package com.sunyard.itp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sunyard.itp.entity.WftPara;
import com.sunyard.itp.mapper.WftMapper;
import com.sunyard.itp.service.WftService;

@Service
public class WftServiceImp implements WftService{
	@Autowired
	private WftMapper wftMapper;
	
	@Override
	public void delete() {
		wftMapper.delete();	
	}
	@Override
	public void add(WftPara params) {
		wftMapper.add(params);
	}
	@Override
	public String queryWft() {
		return wftMapper.query();
		
	}
	
}
