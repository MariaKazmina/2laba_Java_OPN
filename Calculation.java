import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Calculation {

    private String note = "";  //исходная строка
    private  int noteLenght = note.length();
    private  String arr[];
    private String res[];
    private int index = 0;


    /**
     * Заполнение поля note
     */
    void setString(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input note: ");
        note = in.nextLine();
        noteLenght = note.length();

    };




    int getSrtLenght(){

        return noteLenght;

    };

    /**
     * В данной функции строка переводится в массив строк, дабы позже в основной функции было удобно работать с двух-, ...значными числами
     */
    void rebuildStr(){


        arr =  new String[noteLenght];  //массив, в котором потом будет строка
        String middleResult ="";        //строка для промежуточных сохранений
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
                if((t == ')')||(p==4)||(p==3))              //если знак операции или )
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


            }

        }
        if(!middleResult.isEmpty())   //если последний элемент - операнд, то добавить в результат
        addInArray(middleResult);

    };

    /**
     * Добавление в массив элемента
     * @param a String
     */
    void addInArray(String a)
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


        return 0;

    }

    /**
     * Преобразует строку-выражение в строку в ОПН
     */
    void reversePolskNotation()
    {
        int u = 0;

        Stack<String> operation = new Stack<>();
        rebuildStr();      //перестраиваем строку
        res = new String[index];
        for(int i=0; i<index; i++)   //до конца массива
        {
            int p = priority(arr[i]);    //определяем приоритет


            if(p == 0)          //если число
            {
                res[u] = arr[i]; //в результат
                u++;
            }
            if(p == 4)           // * or /
            {
                if(!operation.empty()) {
                    if (priority(operation.peek()) == 4)
                    {
                        String q = operation.pop(); //если приоритет равен, то выталкиваем элемент из стека в выход
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
                        String q = operation.pop(); //если приоритет равен, то выталкиваем элемент из стека в выход
                        res[u] = q;
                        u++;
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

                    while ((operation.peek()).compareTo("(") != 0)       //пока не встретили (
                    {
                        if (operation.peek() == null) {               //если стек пуст, но скобки ( нет
                            System.out.print("Error with ()");
                            return;
                        }
                        String q = operation.pop();
                        res[u] = q; //выталкиваем в результирующий массив
                        u++;

                    }
                    operation.pop(); //убираем ) из стека

            }


        }
        while(!operation.empty()) {
            String q = operation.pop();
            res[u] = q;
            u++;
        }

       index = u;  //переобозначаем значение, так как в процессе ушли скобки


    }

    /**
     * Вычисляет значение выражения в ОПН
     * @return double-переменную
     */
    double calculation()         //вычисление значения выражения в ОПН
    {
        Stack<Double> calc = new Stack<>();


        for(int i = 0; i< index; i++)
        {

            int p = priority(res[i]);

            if(p == 0)
                calc.push(Double.parseDouble(res[i]));      //переводим в число и добавляем
            if(p != 0 )
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


}
