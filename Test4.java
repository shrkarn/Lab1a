/*public class Demo {

	
		
	
	//public class test {
		   public static void main(String args[]) {
		      for(int i = 0; i < 2; i++) {
		         for(int j = 2; j>= 0; j--) {
		            if(i == j) break;
		           System.out.println("i=" + i + " j="+j);
		         }
		      }
		   }
		}*/


/*class TestModifiers{
         int i;
         public static void main (String[] args) {
                   int i; //1
                  private int a = 1; //2
                  protected int b = 1; //3
                  public int c = 1; //4
                  System.out.println(a+b+c); //5
         }
}*/

/*public class Demo {
public static void main (String[] args) {
byte var1 = 127;
byte var2 = 126;
byte result = var1 + var2;
}
}*/

/*class Demo {
public static void main(String[] args) {
try {
doMath(5);
System.out.print("hi");
}
finally { System.out.println(" from finally"); }
}
public static void doMath(int den) {
int num = 7 / den;
}
} */

/*public class Demo{
        public static void main(String[] args){
                int[] a = new int[0];
                System.out.print(a.length);
        }
}*/

/*class Demo
{
public static void main(String []args){
String str = "Hello World";
int []arr = {1,2,3,4,5};
display(arr,str);
}
public static void display(int …arr,String str)
{
for(int num:arr){System.out.println(num);}
System.out.println(str);
}
}*/

/*public class Demo {
        public static void main(String[] args) {
       int arr[5]={1,2,3,4,5};
        for(int ele:arr){
        System.out.print(str);}
}*/
        
        /*public class Demo
{  
    public static void main(String[] args) 
    {
        try 
        { 
            return; 
        } 
        finally 
        {
            System.out.println( "Finally" ); 
        } 
    } 
}
*/

/*public class Demo{ 
  public static void main(String args[]) {
    B b = new B();
  }
}
class A {
  A() {
    System.out.print("A");
  }
}
class B extends A{
  B() {
    System.out.print("B");
  }
}*/

/*class TestModifiers{
         int i;
         
         public static void main (String[] args) {
                   int i; //1
                   private int a = 1; //2
                  protected int b = 1; //3
                  public int c = 1; //4
                  System.out.println(a+b+c); //5
         }
}
*/
/*public class Demo { 
     public static void main(String[] args) { 


    	 //String s = "123456789"; s = (s-"123").replace(1,3,"24") - "89";/*
    	 StringBuffer s = new StringBuffer("123456789"); s.delete(0,3).replace(1,3,"24").delete(4,6);
    	 //StringBuffer s = new StringBuffer("123456789"); s.substring(3,6).delete(1,3).insert(1, "24");
    	 //StringBuilder s = new StringBuilder("123456789"); s.substring(3,6).delete(1,2).insert(1, "24");
    	// StringBuilder s = new StringBuilder("123456789"); s.delete(0,3).delete(1,3).delete(2,5).insert(1, "24"); // insert code here 
          System.out.println(s); 
      } 
 }*/

/* interface Foo {} 
class Alpha implements Foo {} 
class Beta extends Alpha {} 
class Demo extends Beta { 
public static void main( String[] args ) { 
        Beta x = new Beta(); 
      // insert code here Line 7.
//Alpha a = x;
//Foo f = (Delta)x;
Foo f = (Alpha)x;
//Beta b = (Beta)(Alpha)x;
} 
 }
*/

/*public abstract class Demo {
 private int tyres;
 public void setTyres(int tyres) {
   this.tyres = tyres;
 } 
 public int getTyres() {
   return tyres;
 } 
}
public class Car extends Demo {
  @Override
  public int getTyres() {
     return super.getTyres()+1;
  } 
}
public class Main {
  public static void main(String args[]) {
    Car c = new Car(); 
    c.setTyres(5); 
    System.out.println("Tyres = "+c.getTyres()); 
  }
}*/

/*public class Demo {
     public static void main(String[] args) {
        try {
            badMethod();
            System.out.print("E");
            return;
        } catch (RuntimeException ex) {
            System.out.print("B");
        } catch (Exception ex1) {
            System.out.print("C");
        } finally {
            System.out.print("D");
        }
    }
    public static void badMethod() {
        System.out.print("A");
    }
 }*/

/*public class Demo {
    public static void main(String[] args) {
    int a=5, b=7;
    if(a & b > 0 && a | b > 0)
        System.out.println("true");
    else
        System.out.println("false");
    }
}*/

/*class Atom { 
             Atom() { 
        System.out.print("atom "); 
} 
 }
//////////////////////
class Rock extends Atom { 
Rock(String type) { 
System.out.print(type); 
} 
}
//////////////////////

public class Demo extends Rock { 
Demo() { 
super("granite "); 
new Rock("granite "); 
} 
public static void main(String[] a) { 
new Demo(); 
} 
}*/

/*public abstract class Demo { 
private int x; 
private int y; 
public abstract void draw(); 
public void setAnchor(int x, int y) { 
this.x = x; this.y = y; 
} 
              }*/

/* public class Demo {
    public static void main(String[] args) {
        StringBuilder a = new StringBuilder("A");
        StringBuilder b = new StringBuilder("B");
        change(a, b);
        System.out.println(a + "," + b);
    }
    static void change(StringBuilder x, StringBuilder y) {
        y.append(x);
        y = x;
    }
 }*/
/*public class Demo {
String[][] names = {
                 {"Mr.", "Mrs.", "Ms."},
                 {"John", "Gupta", "Hegde", "Khan"},
                 {":M", ":F"}
               };
     System.out.println(names[0][2] + names[1][2]+ names[2][1]);
}*/

/* static void test() throws RuntimeException { 
try { 
System.out.print("test "); 
throw new RuntimeException(); 
} 
catch (Exception ex) { System.out.print("exception "); } 
 } 
 public static void main(String[] args) { 
 try { test(); } 
 catch (RuntimeException ex) { System.out.print("runtime "); } 
 System.out.print("end "); 
 }*/

/*public class Demo {
    static String str;
       public static void main(String[] args) {
        String s;
        if(str=="abc")
           s = str+10;
        System.out.println(str);
        System.out.print(s);
       }
}*/

/*public class Demo {
    public static void main(String[] args) {
        int n = 1;
        outer :
        while (n < 6) {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    System.out.print(i + " ");
                    continue;
                } else {
                    System.out.print(n + " ");
                    break outer;
                }
            }
            n++;
        }
    }
}*/

/*public class Demo {
      public static void main(String [] args) {
         short a, b, c=0;
         a=1;
         b=2;
         c=a+b;
         System.out.println(c);
      }
  }*/