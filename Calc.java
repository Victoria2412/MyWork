
import java.util.Scanner;



public class Main {
    public static int a, b, c, flag;
    public static  char[] operation = {'+', '-', '*', '/'};
    public static String[] arabian = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static String[] roman = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static String[] romanTen ={"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    public static String string;

    public static void main(String[] args) throws Exception {

        System.out.println("Введите выражение:");
        Scanner console = new Scanner(System.in);
        int checkKey = 0;
        string = console.nextLine();                                 // вводим
        string = string.replaceAll("\\s+","");      //удаляем пробелы и т.п.
        checkKey=checkExpr(checkKey);                                        //проверяем
        calc();                                                     // считаем
        print(checkKey);                                            // печатаем

    }
    public static int checkExpr(int checkKey) throws Exception {
        boolean chk1=false;
        int count = 0;
        class ScannerException extends Exception {
            ScannerException(String description){
            super(description);
            }
        }
        //Проверяю наличие математического оператора или превышение
        for (int i = 0; i < string.length(); i++){
            for (int y=0; y < operation.length; y++) {
                if (string.charAt(i) == operation[y]) {
                    chk1 = true;
                    flag = y;
                    count = count+1;
                    }
                }
            }
            if (!chk1){
               throw new ScannerException("//т.к. строка не является математической операцией");
            } else                 if (count>1) {
                 throw new ScannerException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
        //Делю строку на два операнда
                String str1 = Character.toString(operation[flag]);
                String [] operands  = string.split("\\"+str1);

        //проверяю наличие римских или арабских цифр.

            boolean checkRoman1=false;
            boolean checkRoman2=false;
            boolean checkArabian1 = false;
            boolean checkArabian2 = false;
            boolean count1;
            boolean count2;

            for (String element: operands) {
                count1=true; int i=0;
                while (count1  && i< 10) {
                    if (element.equals(roman[i]) && !checkRoman1) {
                    checkRoman1=true;
                    count1 =false;
                    a=i+1;
                    }

                    if (element.equals(roman[i]) && !checkRoman2 && count1) {
                     checkRoman2 = true;
                     b=i+1;
                     count1= false;
                    }
                    i++;
                 }
            }

            for (String element: operands) {
                count2=true; int i=0;
                while (count2  && i< 10) {
                    if (element.equals(arabian[i]) && !checkArabian1){
                        checkArabian1=true;
                        count2=false;
                        a=i+1;
                    }

                    if (element.equals(arabian[i]) && !checkArabian2 && count2){
                        checkArabian2=true;
                        b=i+1;
                        count2=false;
                    }
                    i++;
                }
            }
// проверяю эксепшены
        if (checkRoman1 && checkRoman2 && a< b && flag==1) {
            throw new ScannerException("//т.к. в римской системе нет отрицательных чисел");
        }

        if (checkRoman1 && checkRoman2) {
            checkKey=1;
            return checkKey; // римские цифры подтверждены, возвращаемся
        }
        if (checkArabian1 && checkArabian2) {
            checkKey=2;
            return checkKey; // оба операнда- арабские цифры, возвращаемся
        }
        if (checkRoman1!=checkRoman2 && checkArabian1!=checkArabian2){
                throw new ScannerException("//т.к. используются одновременно разные системы счисления");
        }
        if (checkArabian1 != checkArabian2 | checkRoman1 != checkRoman2){
            throw new ScannerException("//т.к.  вы  ввели некорректное выражение");
        }
        return checkKey;
    }
    public static void calc() {
        switch (flag){
            case 0:
                c=a+b;
                break;
            case 1:
                c=a-b;
                break;
            case 2:
                c=a*b;
                break;
            case 3:

                c=a/b;
                break;
        }

    }
    public static void print(int checkKey){
        String str=String.valueOf(c);
        String str1= new String();
        String part1= new String();
        String part2= new String();
        boolean flag= true;
        switch (checkKey){
            case 2:
                System.out.println("результат: " +c);
                break;
            case 1:
                if (c<=10 && c!=0){
                    System.out.println("результат: " + roman[c-1]);
                } else if (c==0){
                    System.out.println("результат: NULLA");

                } else if (c>10 && str.charAt(1)=='0' ){
                    if (str.length()==3){
                        System.out.println("результат: "+ romanTen[9]);
                        break;
                    }

                    for (int i = 1; i < 9; i++) {
                        String az= Character.toString(str.charAt(0));
                        if (az.equals(arabian[i])) {
                            System.out.println(romanTen[i]);
                            break;
                        }
                    }
                } else if (c>10 && c<20){
                    part1="X";
                    str1=Character.toString(str.charAt(1));
                    int i=0;
                    while (flag && i< roman.length) {
                        if (str1.equals(arabian[i])) {
                            part2 =roman[i];
                            flag= false;
                        }
                        i++;
                    }
                    System.out.println("результат: "+ part1.concat(part2));
            } else  {
                    boolean checkPart1=false;
                    boolean checkPart2=false;
                    char[] charResult=str.toCharArray();
                    for (char element: charResult ) {
                        flag=true; int i=0;
                        while (flag && i<9){
                            if (Character.toString(element).equals(arabian[i]) && !checkPart1){
                                part1=romanTen[i];
                                checkPart1=true;
                                flag=false;
                            }
                            if (Character.toString(element).equals(arabian[i]) && !checkPart2 && flag){
                                part2=roman[i];
                                checkPart2=true;
                                flag=false;
                            }
                            i++;
                        }

                    }
                    System.out.println("результат: "+ part1.concat(part2));
                }

                  break;
        }
    }
}


