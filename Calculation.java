import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Calculation {

    private String note = "";  //исходная строка
    //private int strlenght;
    private  int noteLenght = note.length();
    private  String arr[] = new String[noteLenght];
    private String result="";
    private int index = 0;



  //  double resultOf = 0;   //результат вычислений выражения




    void setString(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input note: ");
        note = in.nextLine();

    };
   /* void strLenght()
    {
        this.strlenght = note.length();

    };

    void getSrtLenght(){
        strlenght = note.length();
        System.out.print(strlenght);

    };*/

    void rebuildStr(){



        String middleResult ="";
        for(int i=0; i<note.length(); i++)
        {
            char t = note.charAt(i);
            if(Character.isDigit(t))
            {
                middleResult+=t;
            }
            else
            {
                addInArray(middleResult);
                middleResult = "";
                addInArray("+t");
            }
        }

    };

    void addInArray(String a)   // тут падает, потому что массив, как и я, тупой
    {
        arr[this.index]+=a;
        this.index++;
    }

    /**
     * Присваивание символу приоритета
     * @param a   переменная массива типа char
     * @return    значение приоритета операции
     */
    int priority(String a){
        if((a =="*") || (a =="/"))
            return 4;
        if((a=="-")||(a=="+"))
            return 3;
        if(a=="(")
            return 2;
        if( a== ")")
            return 1;


        return 0;
    }

    /**
     * Преобразует строку-выражение в строку в ОПН
     */
    void reversePolskNotation()
    {
        Stack<String> operation = new Stack<>();
        rebuildStr();

        for(int i=0; i<note.length(); i++)   //до конца строки
        {
            int p = priority(arr[i]);    //определяем приоритет


            if(p == 0)           //если число
            {

                result += arr[i];          //в результат
            }
            if(p == 4) // * or /
            {
                if(!operation.empty()) {
                    if (priority(operation.peek()) == 4)
                    {
                        result += operation.pop();    //если приоритет равен, то выталкиваем элемент из стека в выход
                        operation.push(arr[i]);       //добавляем новый элемент в стек
                    }

                    if (priority(operation.peek()) < 4)  //если приоритет ниже, то просто добавлем в стек
                        operation.push(arr[i]);
                }
                else   operation.push(arr[i]);

            }
            if(p == 3) //+ or -
            {
                if(!operation.empty())
                {
                    if (priority(operation.peek()) >= 3) {
                        result += operation.pop();    //если приоритет равен, то выталкиваем элемент из стека в выход
                        operation.push(arr[i]);          //добавляем новый элемент в стек
                    }
                    if (priority(operation.peek()) < 3)  //если приоритет ниже, то просто добавлем в стек
                    {
                        operation.push(arr[i]);
                    }

                }
                else operation.push(arr[i]);
            }
            if(p == 2)                    //если открывающаяся скобка
                operation.push(arr[i]);
            if( p == 1 )                    //если встретили закрывающуюся скобку
            {

                    while (operation.peek() != "(")       //пока не встретили (
                    {
                        if (operation.peek() == null) {               //если стек пуст, но скобки ( нет
                            System.out.print("Error with ()");
                            return;
                        }
                        result += operation.pop();   //выталкиваем в результирующий массив

                    }
                    operation.pop();
                //убираем ) из стека
            }


        }
        while(!operation.empty())
            result +=operation.pop();

        if(operation.empty())
        {
            System.out.println(result);
            System.out.println("Empty stack");
        }


    }

    /**
     * Вычисляет значение выражения в ОПН
     * @return double-переменную
     */
    double calculation()         //вычисление значения выражения в ОПН
    {
        Stack<Double> calc = new Stack<>();



        for(int i=0; i<result.length(); i++)
        {
            char t = note.charAt(i);

            int p = priority(""+t);
            if(p == 0)
                calc.push((double)(Character.getNumericValue(t)));      //переводим в число и добавляем
            if(p != 0 )
            {

                double element1 = calc.pop();  //выбираем два последних числа из стека
                double element2 = calc.pop();
                if (t == '*')
                {
                    calc.push(element1 * element2);
                }
                if(t == '/')
                {
                    calc.push(element2 / element1);
                }
                if(t == '+')
                {
                    calc.push(element1 + element2);
                }
                if(t == '-')
                {
                    calc.push(element2 - element1);
                }




            }



        }
        return calc.pop();


    }

    void printArray()    //вывод массива на экран
    {
        for(int i=0; i<index; i++)
            System.out.print(arr[i]);


    }


   /* boolean isNumber(char a)
    {
        if( a >= '0' && a <= '9')
            return true;
        else return false;
    }*/

    boolean isDigit(char a)
    {
        return Character.isDigit(a);

    }




}
