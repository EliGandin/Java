import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /* Initialization */
        ArrayList<Expression> arrayList = new ArrayList<Expression>();
        AtomicExpression a1 = new AtomicExpression(1);
        AtomicExpression a2 = new AtomicExpression(2);
        AtomicExpression a3 = new AtomicExpression(3);

        AdditionExpression ad1 = new AdditionExpression(a1, a2);
        AdditionExpression ad2 = new AdditionExpression(a1, a3);
        AdditionExpression ad3 = new AdditionExpression(a2, a3);

        SubtractionExpression sb1 = new SubtractionExpression(a3, a1);
        SubtractionExpression sb2 = new SubtractionExpression(a3, a2);
        SubtractionExpression sb3 = new SubtractionExpression(a2, a1);

        AdditionExpression ad4 = new AdditionExpression(ad1,ad2);
        AdditionExpression ad5 = new AdditionExpression(ad4, a2);
        AdditionExpression ad6 = new AdditionExpression(a1, ad5);

        SubtractionExpression sb4 = new SubtractionExpression(ad5, ad4);
        SubtractionExpression sb5 = new SubtractionExpression(sb4, ad4);
        SubtractionExpression sb6 = new SubtractionExpression(ad6, sb5);


        /* Adding to the arraylist */
        arrayList.add(a1);
        arrayList.add(a2);
        arrayList.add(a3);
        arrayList.add(ad1);
        arrayList.add(ad2);
        arrayList.add(ad3);
        arrayList.add(sb1);
        arrayList.add(sb2);
        arrayList.add(sb3);
        arrayList.add(ad4);
        arrayList.add(ad5);
        arrayList.add(ad6);
        arrayList.add(sb4);
        arrayList.add(sb5);
        arrayList.add(sb6);

        /* Printing results */
        System.out.println("The Expressions are:\n" + arrayList + "\nNow let's compare them:");

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (arrayList.get(i).equals(arrayList.get(j))){
                    System.out.println(arrayList.get(i).toString() + " == " + arrayList.get(j).toString());
                } else {
                    System.out.println(arrayList.get(i).toString() + " != " + arrayList.get(j).toString());
                }
            }
        }
    }
}
