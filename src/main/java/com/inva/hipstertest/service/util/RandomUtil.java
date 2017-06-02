package com.inva.hipstertest.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;
    private static final int LOG_COUNT = 2;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
    * Generate a reset key.
    *
    * @return the generated reset key
    */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate a random login.
     *
     * @return the generated login for teacher
     */
    public static String generateLogin(String firstName, String lastName, Long idSchool) {
        String returned = transliter(firstName.substring(0,2)
             + lastName) + idSchool.toString();
        if(returned.contains("null")){
            returned = RandomStringUtils.randomAlphabetic(LOG_COUNT);
        }
        return (returned + RandomStringUtils.randomAlphabetic(LOG_COUNT));
    }


    private static String transliter(String some){
        Map<Character,String> map = mapps();
        String s = some.toLowerCase();
        String returns = "";
        for (int i = 0; i < some.length(); i++) {
            returns = returns + map.get(s.charAt(i));
        }
        return returns;
    }

    private static Map<Character, String> mapps(){
        Map<Character, String> map = new HashMap<>();
        map.put('а', "a");map.put('б', "b");map.put('в', "v");
        map.put('г', "h");map.put('д', "d");map.put('е', "e");
        map.put('є', "ye");map.put('ж', "zh");map.put('з', "z");
        map.put('и', "y");map.put('і', "i");map.put('ї', "yi");
        map.put('й', "y");map.put('к', "k");map.put('л', "l");
        map.put('м', "m");map.put('н', "n");map.put('о', "o");
        map.put('п', "p");map.put('р', "r");map.put('с', "s");
        map.put('т', "t");map.put('у', "u");map.put('ф', "f");
        map.put('х', "kh");map.put('ц', "ts");map.put('ч', "ch");
        map.put('ш', "sh");map.put('щ', "shch");map.put('ю', "yu");
        map.put('я', "ya");map.put('ь', "");map.put('\'', "'");
        map.put('`', "");map.put('’', "'");map.put(' ', " ");
        map.put('ґ', "g");map.put('-', "'");
        return map;}

}
