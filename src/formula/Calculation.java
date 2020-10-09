package formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lucifer
 */
public class Calculation {
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
        Pattern numsPattern = Pattern.compile("[0-9]+");
        Pattern charPattern = Pattern.compile("\\*|/|\\+|-");
        for(String factor:midFormula){
            if(numsPattern.matcher(factor).matches()){

            }else if (charPattern.matcher(factor).matches()){

            }else{

            }
        }
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
}
