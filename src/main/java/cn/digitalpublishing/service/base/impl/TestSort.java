package cn.digitalpublishing.service.base.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSort {

	public static void main(String[] args) {
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		set1.add("1");
		set1.add("2");
		set1.add("3");
		set1.add("4");
		set1.add("5");
		
		set2.add("5");
		set2.add("a");
//		set2.add("4");
//		set2.add("3");
		Iterator<String> ite2 = set2.iterator();
		  
		while (ite2.hasNext()) {
			String a = ite2.next();
			if (!set1.contains(a)) {
				System.out.println("if ! "+a);
				break;
			}else{
				System.out.println(a);
			}
			System.out.println("while循环中："+a);
		}
		
	}
	/*static class Sort{
		int a ;
		int b;
		String c;
		
		public Sort() {
			super();
		}
		public Sort(int a, int b, String c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}
		public String getC() {
			return c;
		}
		public void setC(String c) {
			this.c = c;
		}
	}*/
}
/*String a = "a";
String b = "c";
char i = 'a';
char k = 'c';
int q = i;
int w = k;
System.out.println(q);
System.out.println(w);
System.out.println(a.compareTo(b));*/
/*List<Sort> sortList = new ArrayList<Sort>();
sortList.add(new Sort(3,3,"3"));
sortList.add(new Sort(1,1,"1"));
sortList.add(new Sort(4,4,"4"));
sortList.add(new Sort(2,2,"2"));
sortList.add(new Sort(5,5,"5"));

Collections.sort(sortList,new Comparator<Sort>(){  
	@Override
	public int compare(Sort o1, Sort o2) {
		return o1.getC().compareTo(o2.getC());
	}  
}); 
String jsonarray = JSONArray.toJSONString(sortList);
System.out.println(jsonarray);*/

/*

}*/
