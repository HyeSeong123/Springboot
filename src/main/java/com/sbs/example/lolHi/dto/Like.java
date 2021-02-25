package com.sbs.example.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Like {
	private int num;
	private String regDate;
	private String updateDate;
	private int memberNum;
	private int relNum;
	private String relTypeCode;
	private int point;
}
