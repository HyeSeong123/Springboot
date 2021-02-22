package com.sbs.example.lolHi.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.ReplyDao;

@Service
public class replyService {
	@Autowired
	ReplyDao replyDao;
	public int doWrite(Map<String,Object> param) {
		return replyDao.doWrite(param);
	}

}
