package formula;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucifer
 */
public class Formula {





    /**
     * 创建n条每个数字不超过max的公式
     * @param n 需要创建公式的数量
     * @param max 最大数
     * @return 含有n条公式的数组
     */
    public List<String> createFormulas(int n,int max){
        List<String> formulas = new ArrayList<>();
        int choice;
        String fomula;
        while(n>0){
            choice = (int) (Math.random()*3+2);
            fomula = createFormula(choice,max);
            formulas.add(fomula);
            n--;
        }
        return formulas;
    }

    /**
     * 创建一条公式
     * @param number 公式所含被操作数个数
     * @param max 公式最大数字
     * @return 一条公式
     */
    public String createFormula(int number,int max){
        String fomula;
        StringBuffer temp = new StringBuffer();
        List<String> numbers = createOperatedNumbers(number,max);
        List<String> characters = createOperationalCharacter(number-1);
        for(int i=0;i<number-1;i++){
            temp.append(numbers.get(i)).append(" ");
            temp.append(characters.get(i)).append(" ");
        }
        temp.append(numbers.get(number-1));
        fomula = String.valueOf(temp);
        return fomula;
    }

    /**
     *
     * @param number 操作数的数量
     * @param max 基数
     * @return 一条式子所有的操作数
     */
    public List<String> createOperatedNumbers(int number,int max){
        List<String> numbers = new ArrayList<>();
        String temp;
        int choice;//当choice为0时，生成分数，其余生成整数
        for(int i=0;i<number;i++){
            choice = (int)(Math.random()*4);
            temp = choice==0?createFraction(max):String.valueOf((int) (Math.random()*max));
            numbers.add(temp);
        }
        return numbers;
    }

    /**
     *
     * @param number 运算符的数量 可能输入为1,2,3
     * @return 一条式子的所有运算符
     */
    public List<String> createOperationalCharacter(int number){
        List<String> characters = new ArrayList<>();
        if (number==1){
            characters.add(getCharacterRandomly());
        }else if (number==2){
            characters.add(getCharacterRandomly());
            characters.add(getCharacterRandomly());
        }else {
            characters.add(getCharacterRandomly());
            characters.add(getCharacterRandomly());
            characters.add(getCharacterRandomly());
        }
        return characters;
    }

    private String getCharacterRandomly(){
        String character = "";
        int choice;
        choice = (int) (Math.random()*4+1);
        switch (choice) {
            case 1 -> character = "+";
            case 2 -> character = "-";
            case 3 -> character = "*";
            case 4 -> character = "/";
        }
        return character;
    }

    /**
     *
     * @param max 基数
     * @return 分数
     */
    public String createFraction(int max){
        //分子
        int numberator = 0;
        //分母
        int denominator = 0;
        //系数
        int modulus = 0;

        //确保分母不为0和1
        while(denominator<2){
            denominator = (int) (Math.round(Math.random()*(max)));
        }
        //确保系数modulus不大于max和分子不为0
        while(numberator==0||modulus>=max){
            numberator = (int)((Math.round(Math.random()*max))*(Math.round(Math.random()*denominator)));
            modulus = numberator/denominator;
            numberator=numberator%denominator;
        }
        //进行约分
        int factor = seekLeastCommonFactor(numberator,denominator);
        numberator = numberator/factor;
        denominator = denominator/factor;

        return modulus==0? numberator +"/"+ denominator : modulus +"'"+ numberator +"/"+ denominator;
    }

    /**
     * 求最大公因数
     * @param a 参数a
     * @param b 参数b
     * @return 最大公因数
     */
    private int seekLeastCommonFactor(int a, int b){
        int result = 1;
        for (int i = 1; i<=(Math.min(a, b)); i++){
            if (a%i==0&&b%i==0){
                result = i;
            }
        }
        return result;
    }

    private int[] processFraction(String fraction){
        int[] numAndDen = new int[2];
        int modulus;
        int numberator;
        int denominator;
        String[] numbers = fraction.split("'");
        if (numbers.length==1){
            modulus=0;
        }else{
            modulus= Integer.parseInt(numbers[0]);
        }
        String[] factors = numbers[1].split("/");
        numberator = Integer.parseInt(factors[0]);
        denominator = Integer.parseInt(factors[1]);
        if (modulus!=0){
            numberator = numberator + denominator * modulus;
        }
        numAndDen[0] = numberator;
        numAndDen[1] = denominator;
        return numAndDen;
    }
}
