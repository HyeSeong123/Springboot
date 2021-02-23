package com.sbs.example.lolHi.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.dao.ReplyDao;
import com.sbs.example.lolHi.dto.Reply;

@Service
public class ReplyService {
	@Autowired
	ReplyDao replyDao;
	
	public int doWrite(Map<String,Object> param) {
		return replyDao.doWrite(param);
	}

	public List<Reply> getReplies(@RequestParam("relNum") int relNum, @RequestParam("relTypeCode") String relTypeCode) {
		return replyDao.getReplies(relNum, relTypeCode);
	}

	public int doDelete(@RequestParam("num") int num) {
		return replyDao.doDelete(num);
	}

	public Reply getArticleForNum(@RequestParam("num") int num) {
		return replyDao.getArticleForNum(num);
	}

	public void doModify(Map<String, Object> param) {
		replyDao.doModify(param);
	}

}
