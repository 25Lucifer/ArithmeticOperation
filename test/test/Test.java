package test;

import formula.Caculation;
import formula.Calculation;
import formula.Formula;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author lucifer
 */
public class Test {

    public static void main(String[] args) {
        String formula = "1 + 2 - 1'2/3 * 2/3";
//        String[] oprtNums = formula.split(" ");
//        for(String string:oprtNums){
//            System.out.println(string);
//        }
//        String[] orptNums2 = Formula.fractionTransform(oprtNums[4]);
//        for(String string:orptNums2){
//            System.out.println(string);
//        }
//        //------------------
//        int[] cur = Formula.findFraction(oprtNums);
//        for (int i:cur){
//            System.out.println(i);
//        }



//        String[] finOprtNums = new String[oprtNums.length+2];

//        int cur = 4;
//        for(int i=0;i<finOprtNums.length;i++){
//            if (i<cur){
//                finOprtNums[i]=oprtNums[i];
//            }else if (i==cur){
//                finOprtNums[i]=orptNums2[0];
//                finOprtNums[i+1]=orptNums2[1];
//                finOprtNums[i+2]=orptNums2[2];
//                i = i+2;
//            }else{
//                finOprtNums[i]=oprtNums[i-2];
//            }
//        }
//        System.out.println("------------");
//        for(String string:finOprtNums){
//            System.out.println(string);
//        }
//
//        Formula.transFractionInFormula(oprtNums);
//        String p = "*";
//        Pattern charPattern = Pattern.compile("\\*|/|\\+|-");
//        Matcher matcher = charPattern.matcher(p);
//        System.out.println(matcher.matches());
//        System.out.println(Pattern.matches("\\*|/|\\+|-",p));

        Calculation calculation = new Calculation();
        calculation.caculateAnswer(formula);

    }


    @org.junit.Test
    public void test1(){
//        String formula = "5 + 2'3/8 - 1'2/3 * 2'2/3";
        String formula = "5/2 + 1'3/8 - 1/2 * 3/4";

        Caculation calculation = new Caculation();
        String res = calculation.caculateAnswer(formula);
        System.out.println(res);
    }

    @org.junit.Test
    public void createFormulas(){
        Formula formula = new Formula();
        Caculation calculation = new Caculation();
        String result;

        List<String> formulas = formula.createFormulas(20,15);
        List<String> answers = new ArrayList<>();
        for(String string : formulas){
            result = calculation.caculateAnswer(string);
            answers.add(string+" = "+ result);
        }
        FileUtil fu = new FileUtil();
        fu.fileWriter(formulas,"/Users/maple/Desktop/Execrise.txt");
        fu.fileWriter(answers,"/Users/maple/Desktop/Answers.txt");

    }


}
