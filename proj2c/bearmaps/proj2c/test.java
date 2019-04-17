package bearmaps.proj2c;

public class test {
    public static void main(String[] args) {
        String a = "85°C Bakery Cafe";
        System.out.println(a.replaceAll("[^a-zA-Z0-9]", ""));
        System.out.println(a);
    }
}
