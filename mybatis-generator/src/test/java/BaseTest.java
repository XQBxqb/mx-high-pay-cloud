import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 昴星
 * @date 2023-09-14 19:13
 * @explain
 */
public class BaseTest {
    @Test
    public void hash(){
        animal.type();

    }
    @Data
    @ToString
    class animal implements Comparable<animal>{
        public animal(int age, int sex) {
            this.age = age;
            this.sex = sex;
        }

        public  int age=1;
        public  int sex=1;

        @Override
        public int compareTo(animal o) {
            return this.age-o.age;
        }

        static void type(){
            Type[] genericInterfaces = animal.class.getGenericInterfaces();
            for(Type type : genericInterfaces){
                if (type instanceof ParameterizedType) {
                    ParameterizedType pa = (ParameterizedType) type;
                    if( pa.getRawType() == Comparable.class ) {
                        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                        if(actualTypeArguments.length == 1){
                            System.out.println("Class : "+actualTypeArguments[0]);
                        }
                    }
                }

            }
        }
    }
}
