package com.sbs.example.lolHi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	private String loginPw;
	@JsonIgnore
	private String authKey;
	private int adminLevel;
}
