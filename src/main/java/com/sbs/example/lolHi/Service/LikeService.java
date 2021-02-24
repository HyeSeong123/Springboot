package com.sbs.example.lolHi.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.LikeDao;

@Service
public class LikeService {
	@Autowired
	private LikeDao likeDao;
	
	public void doLike(Map<String, Object> param) {
		likeDao.doLike(param);
	}

}
