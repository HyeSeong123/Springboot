package com.sbs.example.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sbs.example.lolHi.dto.Reply;

@Mapper
public interface ReplyDao {

	int doWrite(Map<String,Object>param);

	List<Reply> getReplies(int relNum, String relTypeCode);

	int doDelete(int num);

	Reply getArticleForNum(int num);

	void doModify(Map<String, Object> param);
	
}
