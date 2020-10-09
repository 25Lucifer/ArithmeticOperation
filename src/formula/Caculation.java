package formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lucifer
 */
public class Caculation {
    public String caculateAnswer(String formula){
        List<String> factors = new ArrayList<>();
        //对fomula进行拆分，数字转化成int，真分数转化成假分数，符号依旧保存为String
        String[] oprtFactors = formula.split(" ");
        transFractionInFormula(oprtFactors);
        for(String string:oprtFactors){
            System.out.println(string);
        }
        //将中缀表达式变成后缀表达式
        String[] afterFomula = transFormula(oprtFactors);
        for(String string:afterFomula){
            System.out.println(string);
        }
        //计算后缀表达式的结果
        formula = caculate(afterFomula);
        return formula;
    }

    private String caculate(String[] afterFormula){
        Stack<Fraction> nums = new Stack<>();
        String[] fractions;


        Fraction operator1,operator2,res;

        int numberator1=0,numberator2=0,denominator1=1,denominator2=1;
        int numberatorRes=0,denominatorRes=1;


        Pattern numPattern = Pattern.compile("[0-9]+");
        Pattern fracPattern = Pattern.compile("[0-9]+/[0-9]+");
        for(String factor:afterFormula){
            if (numPattern.matcher(factor).matches()){
                Fraction fraction = new Fraction();
                fraction.numberator = Integer.parseInt(factor);
                fraction.denominator = 1;
                nums.add(fraction);
            }else if(fracPattern.matcher(factor).matches()){
                Fraction fraction = new Fraction();
                fractions = factor.split("/");
                fraction.numberator = Integer.parseInt(fractions[0]);
                fraction.denominator = Integer.parseInt(fractions[1]);
                nums.add(fraction);
            }else {
                operator2 = nums.pop();
                operator1 = nums.pop();
                res = new Fraction();
                switch (factor){
                    case "+":
                        res.numberator = operator1.numberator*operator2.denominator+operator2.numberator*operator1.denominator;
                        res.denominator = operator1.denominator*operator2.denominator;
                        nums.add(res);
                        break;
                    case "-":
                        res.numberator = operator1.numberator*operator2.denominator-operator2.numberator*operator1.denominator;
                        res.denominator = operator1.denominator*operator2.denominator;
                        nums.add(res);
                        break;
                    case "*":
                        res.numberator = operator1.numberator*operator2.numberator;
                        res.denominator = operator1.denominator*operator2.denominator;
                        nums.add(res);
                        break;
                    case "/":
                        res.numberator = operator1.numberator*operator2.denominator;
                        res.denominator = operator1.denominator*operator2.numberator;
                        nums.add(res);
                        break;
                }
            }
        }
        Fraction result = nums.pop();
        Formula formula = new Formula();
        int min = formula.seekLeastCommonFactor(result.numberator,result.denominator);
        result.numberator/=min;result.denominator/=min;
        return result.numberator+"/"+result.denominator;
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
        Pattern numsPattern = Pattern.compile("[0-9]+|[0-9]+/[0-9]+");
        Pattern charPattern = Pattern.compile("\\*|/|\\+|-");
        Pattern level = Pattern.compile("\\*|/");
        int topStackLevel=1;
        int factorLevel;
        int i=0;
        boolean flag = true;
        for(String factor:midFormula){
            if(numsPattern.matcher(factor).matches()){
                afterFormula[i] = factor;
                i++;
            }else if (charPattern.matcher(factor).matches()){
                //判断自身元素
                if (level.matcher(factor).matches()){
                    factorLevel=1;
                }else {
                    factorLevel=0;
                }

                while(flag){

                    if(charcter.size()==0){
                        charcter.add(factor);
                        break;
                    }else{
                        //先求栈顶元素优先级
                        if(level.matcher(charcter.peek()).matches()){
                            topStackLevel=1;
                        }else{
                            topStackLevel=0;
                        }
                        //判断栈顶元素是否需要出栈
                        if (factorLevel<=topStackLevel){
                            afterFormula[i] = charcter.pop();
                            i++;
                        }else{
                            charcter.add(factor);
                            break;
                        }
                    }
                }
            }
        }
        while(i< midFormula.length){
            afterFormula[i]=charcter.pop();
            i++;
        }
        //若为数字存入数组中
        //判断运算符的优先级 若栈顶运算符优先级高或相同优先级 则栈顶元素出栈 存入数组中 反之进栈
        return afterFormula;
    }


    /**
     * 将中缀表达式数组中的真分数全部替代成假分数
     * @param oprtNums 中缀表达式数组
     */
    private void transFractionInFormula(String[] oprtNums){
        int[] cur = findFraction(oprtNums);
        int count = 0;
        for (int j : cur) {
            if (j != -1) count++;
        }
        for(int i=0;i<count;i++){
            oprtNums[cur[i]] = fractionTransform(oprtNums[cur[i]]);
        }
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
    private String fractionTransform(String fraction){
        //分子
        int numberator = 0;
        //分母
        int denominator = 0;
        //系数
        int modulus = 0;
        //返回的字符串
        String oprtFactor;

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
        oprtFactor = String.valueOf(numberator)+"/"+String.valueOf(denominator);
        return oprtFactor;
    }
}
