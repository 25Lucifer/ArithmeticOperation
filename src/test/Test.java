package test;

import formula.Formula;


/**
 * @author lucifer
 */
public class Test {

    public static void main(String[] args) {
        String formula = "1 + 2 - 1'2/3 * 2/3";
        String[] oprtNums = formula.split(" ");
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

        Formula.transFractionInFormula(oprtNums);
    }


}
