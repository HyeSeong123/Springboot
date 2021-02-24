package com.sbs.example.lolHi.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.dao.ReplyDao;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.Reply;

@Service
public class ReplyService {
	@Autowired
	ReplyDao replyDao;

	public int doWrite(Map<String, Object> param) {
		return replyDao.doWrite(param);
	}

	public List<Reply> getReplies(@RequestParam("relNum") int relNum, @RequestParam("relTypeCode") String relTypeCode, Member actorMember) {
		List<Reply> replies = replyDao.getReplies(relNum, relTypeCode);
		
		for (Reply reply : replies) {
			if (reply.getExtra() == null) {
				reply.setExtra(new HashMap<>());
			}

			boolean actorCanDelete = false;
			
			if(actorMember != null) {
				actorCanDelete = actorMember.getNum() == reply.getMemberNum();
			}
			
			boolean actorCanModify = actorCanDelete;
			
			reply.getExtra().put("actorCanDelete", actorCanDelete);
			reply.getExtra().put("actorCanModify", actorCanModify);
		}
		return replies;
	}

	public List<Reply> getRepliesNum(@RequestParam("relTypeCode") String relTypeCode, Member actorMember) {
		List<Reply> replies = replyDao.getRepliesNum(relTypeCode);
		
		for (Reply reply : replies) {
			if (reply.getExtra() == null) {
				reply.setExtra(new HashMap<>());
			}

			boolean actorCanDelete = false;
			
			if(actorMember != null) {
				actorCanDelete = actorMember.getNum() == reply.getMemberNum();
			}
			
			boolean actorCanModify = actorCanDelete;
			
			reply.getExtra().put("actorCanDelete", actorCanDelete);
			reply.getExtra().put("actorCanModify", actorCanModify);
		}
		return replies;
	}
	
	public int doDelete(@RequestParam("num") int num) {
		return replyDao.doDelete(num);
	}

	public Reply getArticleForNum(@RequestParam("num") int num, Member actorMember) {
		Reply reply = replyDao.getArticleForNum(num);

		if (reply.getExtra() == null) {
			reply.setExtra(new HashMap<>());
		}

		boolean actorCanDelete = false;

		if (actorMember != null) {
			actorCanDelete = actorMember.getNum() == reply.getMemberNum();
		}

		boolean actorCanModify = actorCanDelete;

		reply.getExtra().put("actorCanDelete", actorCanDelete);
		reply.getExtra().put("actorCanModify", actorCanModify);

		return reply;
	}

	public void doModify(Map<String, Object> param) {
		replyDao.doModify(param);
	}


}