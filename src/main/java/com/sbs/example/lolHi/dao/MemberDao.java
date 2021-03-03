package com.sbs.example.lolHi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.example.lolHi.dto.Member;

@Mapper
public interface MemberDao {

	int join(Map<String, Object> param);

	Member getMemberByLoginId(@Param("loginId") String loginId);

	Member getMemberByNum(@Param("num") int loginedMemberNum);

	int doModify(Map<String, Object> param);

	Member getMemberByNameAndEmail(Map<String, Object> param);

	Member getMemberByEmail(@Param("email") String email);

	Member getMemberByLoginIdAndEmail(@Param("loginId") String loginId, @Param("email") String email);

}