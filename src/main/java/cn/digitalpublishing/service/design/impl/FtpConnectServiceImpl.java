package cn.digitalpublishing.service.design.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.FtpConnectMapper;
import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.service.design.FtpConnectService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("ftpConnectService")
public class FtpConnectServiceImpl implements FtpConnectService {
	
	@Resource(name="ftpConnectMapper")
    private FtpConnectMapper ftpConnectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(ftpConnectMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<FTPConnect> getFTPConnectList(Map<String, Object> condition)throws Exception {
		return ftpConnectMapper.findListByCondition(condition);
	}

	@Override
	public FTPConnect getFTPConnectById(String id) throws Exception {
		return ftpConnectMapper.findById(id);
	}

	@Override
	public void updateFTPConnect(FTPConnect obj) throws Exception {
		ftpConnectMapper.updateById(obj);
	}

	@Override
	public void insertFTPConnect(FTPConnect obj) throws Exception {
		ftpConnectMapper.insert(obj);
	}

	@Override
	public void deleteFTPConnectById(String id) throws Exception {
		ftpConnectMapper.deleteById(id);
	}

}
