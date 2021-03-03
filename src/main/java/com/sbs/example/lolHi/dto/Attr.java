package com.sbs.example.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attr {
	private int num;
	private String regDate;
	private String updateDate;
	private String relTypeCode;
	private int relNum;
	private String typeCode;
	private String type2Code;
	private String value;
}
