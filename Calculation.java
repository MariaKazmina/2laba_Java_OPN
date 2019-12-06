import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Calculation {

    private String note = "";  //исходная строка

    private  int noteLenght = note.length();
    private  String arr[];
   // private String result="";
    private String res[];
    private int index = 0;



  //  double resultOf = 0;   //результат вычислений выражения




    void setString(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input note: ");
        note = in.nextLine();
        noteLenght = note.length();

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


        arr =  new String[noteLenght];
        String middleResult ="";
        int p=0;



        for(int i=0; i<note.length(); i++)
        {
            char t = note.charAt(i);


            if(Character.isDigit(t))          //если число
            {

                middleResult+=Character.toString(t);
            }
            else
            {
                if (t=='(')
                    addInArray(Character.toString(t));
                p = priority(Character.toString(t));
                if((t == ')')||(p==4)||(p==3))
                {
                    if(middleResult.isEmpty())
                    {
                        addInArray(Character.toString(t));
                    }
                    else
                    {
                        addInArray(middleResult);
                        middleResult = "";
                        addInArray(Character.toString(t));
                    }

                }
                /*p = priority(Character.toString(t));
                if(((p==4)||(p==3)) ){                 //&& middleResult.isEmpty()

                    addInArray(middleResult);
                    middleResult = "";
                    addInArray(Character.toString(t));
                }*/

            }

        }
        if(!middleResult.isEmpty())
        addInArray(middleResult);

    };

    void addInArray(String a)   // тут падает, потому что массив, как и я, тупой
    {

        arr[this.index]=a;
        this.index++;
    }


    /**
     * Присваивание символу приоритета
     * @param a   переменная массива типа char
     * @return    значение приоритета операции
     */
    int priority(String a){
        if((a.compareTo("*")==0) || (a.compareTo("/")==0))
            return 4;
        if((a.compareTo("-")==0)||(a.compareTo("+")==0))
            return 3;
        if(a.compareTo("(")==0)
            return 2;
        if( a.compareTo(")")==0)
            return 1;


        return 5;

    }

    /**
     * Преобразует строку-выражение в строку в ОПН
     */
    void reversePolskNotation()
    {
        int u = 0;

        Stack<String> operation = new Stack<>();
        rebuildStr();
        res = new String[index];
        for(int i=0; i<index; i++)   //до конца массива
        {
            int p = priority(arr[i]);    //определяем приоритет


            if(p == 5)           //если число
            {

               // result += arr[i];          //в результат
                res[u] = arr[i];
                u++;
            }
            if(p == 4) // * or /
            {
                if(!operation.empty()) {
                    if (priority(operation.peek()) == 4)
                    {
                        String q = operation.pop();
                      //  result += q;  //если приоритет равен, то выталкиваем элемент из стека в выход
                        res[u] = q;
                        u++;
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
                        String q = operation.pop();
                       // result += q;  //если приоритет равен, то выталкиваем элемент из стека в выход
                        res[u] = q;
                        u++;   //если приоритет равен, то выталкиваем элемент из стека в выход
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
                        String q = operation.pop();
                       // result += q;  //если приоритет равен, то выталкиваем элемент из стека в выход
                        res[u] = q;
                        u++;  //выталкиваем в результирующий массив

                    }
                    operation.pop();
                //убираем ) из стека
            }


        }
        while(!operation.empty()) {
            String q = operation.pop();
          //  result += q;
            res[u] = q;
            u++;
        }

       /* if(operation.empty())
        {
            System.out.println(result);
            System.out.println("Empty stack");
        }*/


    }

    /**
     * Вычисляет значение выражения в ОПН
     * @return double-переменную
     */
    double calculation()         //вычисление значения выражения в ОПН
    {
        Stack<Double> calc = new Stack<>();

        for(int i=0; i<index; i++)
        {


            int p = priority(res[i]);
            if(p == 5)
                calc.push(Double.parseDouble(res[i]));      //переводим в число и добавляем
            if(p != 5 )
            {

                double element1 = calc.pop();  //выбираем два последних числа из стека
                double element2 = calc.pop();
                if (res[i].compareTo("*") == 0)
                {
                    calc.push(element1 * element2);
                }
                if(res[i].compareTo("/") == 0)
                {
                    calc.push(element2 / element1);
                }
                if(res[i].compareTo("+") == 0)
                {
                    calc.push(element1 + element2);
                }
                if(res[i].compareTo("-") == 0)
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
