package cn.digitalpublishing.service.imp.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.imp.ImportRoleDetailMatchMapper;
import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;
import cn.digitalpublishing.service.imp.ImportRoleDetailMatchService;

@Service("importRoleDetailMatchService")
public class ImportRoleDetailMatchServiceImpl implements ImportRoleDetailMatchService {
	
	@Resource(name="importRoleDetailMatchMapper")
    private ImportRoleDetailMatchMapper importRoleDetailMatchMapper;

	@Override
	public void deleteById(String id) {
		importRoleDetailMatchMapper.deleteById(id);
	}

	@Override
	public ImportRoleDetailMatch findByDetailId(String detailId) {
		return importRoleDetailMatchMapper.findByDetailId(detailId);
	}

	@Override
	public void insert(ImportRoleDetailMatch match) {
		importRoleDetailMatchMapper.insert(match);
	}
	
	
}
