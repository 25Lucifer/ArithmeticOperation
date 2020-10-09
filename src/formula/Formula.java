package formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lucifer
 */
public class Formula {



    private String caculateAnswer(String formula){
        List<String> factors = new ArrayList<>();
        //对fomula进行拆分，数字转化成int，分数转化成数字和符号，符号依旧保存为String
        String[] oprtFactors = formula.split(" ");
        String[] finOprtNums = transFractionInFormula(oprtFactors);
        //将中缀表达式变成后缀表达式

        //计算后缀表达式的结果
        return formula;
    }


    /**
     * 将中缀表达式转换为后缀表达式
     * @param midFormula 中缀表达式数组
     * @return 后缀表达式数组
     */
    private String[] transFormula(String[] midFormula){
        String[] afterFormula = new String[midFormula.length];
        Stack<String> charcter = new Stack<>();
        //用正则表达式匹配是数字还是运算符
        //若为数字存入数组中
        //判断运算符的优先级 若栈顶运算符优先级高或相同优先级 则栈顶元素出栈 存入数组中 反之进栈
        return afterFormula;
    }


    /**
     * 将中缀表达式数组中的分数元素全部替代成由分子、除号、分母表示
     * @param oprtNums 中缀表达式数组
     */
    private String[] transFractionInFormula(String[] oprtNums){
        int[] cur = findFraction(oprtNums);
        int count = 0;
        for (int i=0;i<cur.length;i++){
            if (cur[i]!=-1)count++;
        }
        String[] finOprtNums = new String[oprtNums.length+2*count];
        String[] temp = oprtNums;
        for(int i=0;i<count;i++){
            temp = transSingleFranction(temp,cur[i]);
            cur[i+1]+=2;
        }
        finOprtNums = temp;
        for(String string:finOprtNums){
            System.out.println(string);
        }
        return finOprtNums;
    }


    /**
     * 将数组中的单个是分数的元素替代成表示分数的多个元素
     * @param oprtNums 被操作数组
     * @param cur 分数下标
     * @return 操作后的数组
     */
    private String[] transSingleFranction(String[] oprtNums,int cur){
        String[] finOprtNums = new String[oprtNums.length+2];
        String[] orptNums2 = fractionTransform(oprtNums[cur]);
        for(int i=0;i<finOprtNums.length;i++){
            if (i<cur){
                finOprtNums[i]=oprtNums[i];
            }else if (i==cur){
                finOprtNums[i]=orptNums2[0];
                finOprtNums[i+1]=orptNums2[1];
                finOprtNums[i+2]=orptNums2[2];
                i = i+2;
            }else{
                finOprtNums[i]=oprtNums[i-2];
            }
        }
        return finOprtNums;
    }



    /**
     * 通过正则表达式匹配出待操作数组中的分数并返回他们的位置
     * @param factors 包含所有操作数和运算符的中缀表达式数组
     * @return 分数所在的坐标
     */
    private int[] findFraction(String[] factors){
        int[] cur = new int[4];
        int i = 0;
        int j = 0;
        for(int k=0;k<4;k++){
            cur[k]=-1;
        }
        Pattern pattern = Pattern.compile("[0-9]+'[0-9]+/[0-9]+|[0-9]+/[0-9]+");
        Matcher matcher;
        for(String factor:factors){
            matcher = pattern.matcher(factor);
            if(matcher.matches()){
                cur[i] = j;
                i++;
            }
            j++;
        }
        return cur;
    }


    /**
     * 将分数分成分子、除号、分母的字符串数组
     * @param fraction 分数
     * @return 字符串数组
     */
    private String[] fractionTransform(String fraction){
        //分子
        int numberator = 0;
        //分母
        int denominator = 0;
        //系数
        int modulus = 0;
        //返回的字符串数组
        String[] oprtFactors = new String[3];

        String[] nums = fraction.split("'");
        if (nums.length==1){
            String[] nums2 = nums[0].split("/");
            numberator = Integer.parseInt(nums2[0]);
            denominator = Integer.parseInt(nums2[1]);
        }else {
            modulus = Integer.parseInt(nums[0]);
            String[] nums2 = nums[1].split("/");
            numberator = Integer.parseInt(nums2[0]);
            denominator = Integer.parseInt(nums2[1]);
            numberator = numberator + denominator * modulus;
        }

        oprtFactors[0] = String.valueOf(numberator);
        oprtFactors[1] = "/";
        oprtFactors[2] = String.valueOf(denominator);

        return oprtFactors;
    }

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
