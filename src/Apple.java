import java.util.*;

public class Apple {
    private String color;

    public Apple(String color) {
        this.color = color;
    }

    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (!(obj instanceof Apple))
            return false;
        if (obj == this)
            return true;
        return this.color.equals(((Apple) obj).color);
    }

    public int hashCode(){
        return this.color.hashCode();
    }

    public static void main(String[] args) {
        Apple a1 = new Apple("green");
        Apple a2 = new Apple("red");

        //hashMap stores apple type and its quantity
        HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
        m.put(a1, 10);
        m.put(a2, 20);
        System.out.println(m.get(new Apple("green")));

        int[] array = {1, 2, 3};
        List<int[]> arrayList = Arrays.asList(array);

        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.add(1);
        arrayList1.add(100);
        arrayList1.toArray();


        int[] arr = {1,  3, 4};
        int[] arr1 = new int[3];
        System.out.println("----------------");
        for(int i : arr){
            System.out.println(i);
        }
        for(int j : arr1){
            System.out.println(j);
        }
        System.out.println("------------------");
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(10);
        arrayList2.add(11);
        System.out.println(arrayList2);
        arrayList2.add(1,12);
        System.out.println(arrayList2);

        System.out.println("---------------------");
        Iterator<Integer> iterator = arrayList2.iterator();
        while(iterator.hasNext()){
            Integer i = iterator.next();
            System.out.println(i);
        }

    }
}
