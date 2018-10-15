
	/*public static Collection get() { 
		        Collection sorted = new LinkedList(); 
		          sorted.add("B"); sorted.add("C"); sorted.add("A"); 
		          return sorted; 
		 } 
		 public static void main(String[] args) { 
		          for (Object obj: get()) { 
		                    System.out.print(obj + ", "); 
		          } 
		 }*/ 

		/* public class Test6 {
			 static class PowerOutage extends Exception {}
			 static class Thunderstorm extends Exception {}
			 public static void main(String[] args) {
			 try {
			 new Test6().listen();
			 System.out.println("a");
			 } catch(PowerOutage | Thunderstorm e) {
			 e = new PowerOutage();
			 System.out.println("b");
			 } finally { System.out.println("c"); }
			 }
			 public void listen() throws PowerOutage, Thunderstorm{ }
			 }*/

/*//package test;
import java.util.ArrayList;
public class Test6 {
public static void main(String[] args) {
ArrayList<String> collector = new ArrayList<String>( );
collector.add("Study");
collector.add(Integer.toOctalString(Integer.MAX_VALUE));
ArrayList<String> names = new ArrayList<>( );
names.addAll(collector);
names.add("Study");
names.add(null);
System.out.println(names.size( ));
}
}*/

/*import java.util.Iterator;
import java.util.TreeSet;
public class Test6 {
public static void main(String... args) {

TreeSet s1 = new TreeSet();
s1.add("one");
s1.add("two");
s1.add("three");
s1.add("one");
 Iterator it = s1.iterator();
 while (it.hasNext() ) {
 System.out.print( it.next() + " " );
 }
}
}*/

/*public static Collection get() { 
          Collection sorted = new LinkedList(); 
          sorted.add("B"); sorted.add("C"); sorted.add("A"); 
          return sorted; 
 } 
 public static void main(String[] args) { 
           Collection list = get();
          for (Object obj: list) { 
                    System.out.print(obj + ", "); 
          } 
 }*/

/*import java.util.*;
 public class Test6 {
 public static void main(String[] args) {
 // insert code here
	 //Set set = new TreeSet();
	 //Set set = new HashSet();
	 //Set set = new SortedSet();
	 //List set = new SortedList();
	 Set set = new LinkedHashSet();
 set.add(new Integer(2));
 set.add(new Integer(1));
 System.out.println(set);
 }
 }
*/
/* public class Test6 {
 public static int sum(List list) { 
 int sum = 0; 
 for ( Iterator iter = list.iterator(); iter.hasNext(); ) { 
 int i = ((Integer)iter.next()).intValue(); 
 sum += i; 
 } 
 return sum; 
 }
 }*/
/*import java.util.*;

public class Test6{
public static void main(String[] args) {
ArrayList<String> strings = new ArrayList<String>();
strings.add("aAaA");
strings.add("AaA");
 strings.add("aAa");
 strings.add("AAaa");
 Collections.sort(strings);
 for (String s : strings) { System.out.print(s + " "); }
 }
} 
*/

/*import java.util.ArrayList;
 import java.util.List;
 public class Test6{ 
       public static void main(String args[]) {
        List<Integer> list = new ArrayList<Integer>(); 
    list.add(0, 59);
    int total = list.get(0);
    System.out.println(total);  
     }
}*/

/*import java.util.ArrayList;
import java.util.List;
public class Test6 {     
      public static void main(String[] args) {
           List<String> list = new ArrayList<>();
           list.add("English");
           list.add("Hindi");
           list.add("Kannada");
           list.add("Marathi");
           list.add("Tamil");
           list.add("Telugu");
           list.add(2,"Oriya");
           System.out.print(list.get(4));
           list.remove(3);
           System.out.print(list.get(4));
      }
}*/

/*public class Test6 {
public static void main(String... args) {

Set s = new TreeSet();
 s.add("7");
 s.add(9);
 Iterator itr = s.iterator();
 while (itr.hasNext())
 System.out.print(itr.next() + " ");
}
}*/

//public class Test6 {
/*public static void main(String args[]){
ArrayList<String> list= new ArrayList<>();
list.add("SE");
list.add("EE");
list.add("ME");
list.add("SE");
list.add("EE");

list.remove("SE");
System.out.println("Values are : "+ list);
}*/
	
	/*public void genNumbers() {
		 ArrayList numbers = new ArrayList();
		 for (int i=0; i<10; i++) {
		 int value = i * ((int) Math.random());
		 Integer intObj = new Integer(value);
		 numbers.add(intObj);
		 }
		 System.out.println(numbers);
		 }*/
	
	
	
	/* ArrayList<Integer> list = new ArrayList<>(1);
	 list.add(1001);
	 list.add(1002);
	 System.out.println(list.get(list.size()));*/
	
	 /*public void genNumbers() {
		 ArrayList numbers = new ArrayList();
		for (int i=0; i<10; i++) {
		 int value = i * ((int) Math.random());
		 Integer intObj = new Integer(value);
		 numbers.add(intObj);
		  }
		  System.out.println(numbers);
		  }*/
	
	/*public List<ShowBean> showAllMovies() throws BookingException{
		  List<ShowBean> shows = new ArrayList<ShowBean>();
		  String sql = "SELECT * FROM  ShowDetails";
		  ShowBean showBean = null;
		   Statement statement = con.createStatement();
		  ResultSet rSet = statement.executeQuery(sql);
		rSet.next();
		   while (rSet.next()) {
		    showBean = new ShowBean();
		    showBean.setShowId(rSet.getString("ShowId"));
		showBean.setShowName(rSet.getString("ShowName"));
		shows.add(showBean);
		}}*/
	
	/*String sql="SELECT empseq.nextval FROM dual";
	  Statement stmt = con.createStatement();
	  ResultSet rSet = stmt.executeQuery(sql);
	  //line 3
	  return rSet.getInt(1);*/
