package com.sbs.example.lolHi.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.LikeDao;
import com.sbs.example.lolHi.dto.Like;

@Service
public class LikeService {
	@Autowired
	private LikeDao likeDao;
	
	public void doLike(Map<String, Object> param) {
		likeDao.doLike(param);
	}

	public Like getLikeByParam(Map<String, Object> param) {
		return likeDao.getLikeByParam(param);
	}

	public Like getLikeByParam(int num, int loginedMemberNum) {
		Map<String, Object> param = new HashMap<>();
		
		param.put("num", num);
		param.put("memberNum", loginedMemberNum);
		
		return getLikeByParam(param);
	}

	public void doDelete(Map<String, Object> param) {
		likeDao.doDelete(param);
	}
	

}
