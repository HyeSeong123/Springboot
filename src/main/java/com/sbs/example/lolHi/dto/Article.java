package com.sbs.example.lolHi.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private int num;
	private String regDate;
	private String updateDate;
	private int memberNum;
	private String title;
	private String body;
	private int replyNum;
	private int likeNum;
	
	private Map<String, Object> extra;
	
}
