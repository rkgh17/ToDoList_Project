package com.hjh.todolist.api.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
	
	//mapper를 사용하는 방식
	void insertXml(Map<String, Object> map);

	Map selectXml(Map<String, Object> map);
	
}
