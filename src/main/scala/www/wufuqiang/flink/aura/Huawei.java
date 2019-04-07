package www.wufuqiang.flink.aura;

import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author wufuqiang
 * @ date 2019/4/3/003 - 19:20
 **/
public class Huawei {

    public static void main(String[] args){
        String str = "aba33333555555552642272457524727652" ;
        qucong(str) ;

    }

    public static String zuoyi(String str){
        int length = str.length() ;
        StringBuffer result = new StringBuffer() ;

        int index = 10 % length ;
        if(index == 0){
            return str ;
        }
        index -=1 ;
        result.append(str,index,length) ;
        result.append(str,0,index) ;
        return result.toString() ;

    }

    public static void qucong(String str){
        str = str.trim() ;
        int length = str.length() ;
        int index = 0 ;
        StringBuffer hefa = new StringBuffer() ;
        List<Character> list = new ArrayList<Character>() ;
        while(index < length){
            char tmp = str.charAt(index) ;
            if(!((tmp >= '0' && tmp <= '9')||(tmp >= 'a' && tmp <= 'z')||(tmp >= 'A' && tmp <='Z'))){
                 System.out.println(str) ;
                 return ;
            }else{
                if(!list.contains(tmp)){
                    hefa.append(tmp) ;
                    list.add(tmp) ;
                }
            }


            index+=1 ;
        }
        String result = zuoyi(hefa.toString()) ;
        System.out.println(result) ;
        char [] cs = result.toCharArray() ;
        Arrays.sort(cs);
        System.out.println(new String(cs));
    }

}
