package com.design;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestSort {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("a", "1");
		map.put("a", "2");
		map.put("b", "e");
		map.put("c", "w");
		map.put("d", "q");
		System.out.println(map);
		Set<String> set = map.keySet();
		for(String str : set){
			System.out.println(str);
		}
	}
}