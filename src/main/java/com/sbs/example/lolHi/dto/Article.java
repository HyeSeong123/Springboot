package com.sbs.example.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
	public int num;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
}
