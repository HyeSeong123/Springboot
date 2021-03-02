package com.sbs.example.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
	private int num;
	private String regDate;
	private String updateDate;
	private String name;
	private String nickname;
	private String email;
	private String hpNum;
	private String loginId;
	private String loginPw;
	private int adminLevel;
}
