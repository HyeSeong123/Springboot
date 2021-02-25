package com.sbs.example.lolHi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sbs.example.lolHi.dto.Like;

@Mapper
public interface LikeDao {

	void doLike(Map<String, Object> param);

	Like getLikeByParam(Map<String, Object> param);

	void doDelete(Map<String, Object> param);
	
}
