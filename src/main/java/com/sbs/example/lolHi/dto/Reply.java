package com.sbs.example.lolHi.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int num;
	private String regDate;
	private String updateDate;
	private int memberNum;
	private String relNum;
	private String relTypeCode;
	private String body;

	private Map<String, Object> extra;
}
