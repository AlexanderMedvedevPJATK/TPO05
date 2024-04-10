package com.s28572.time;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NumericController {

    @GetMapping("/converter")
    public String converter() {
        return "converter.html";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam int value,
                          @RequestParam(name = "from-base") int fromBase,
                          @RequestParam(name = "to-base") int toBase) {
        int decimal = convertToDecimal(String.valueOf(value), fromBase);
        String res = convertToBase(decimal, toBase);
        System.out.println("INIT VALUE: " + value);
        System.out.println("DEC: " + decimal);
        System.out.println("BIN: " + Integer.toBinaryString(decimal));
        System.out.println("OCT: " + Integer.toOctalString(decimal));
        System.out.println("HEX: " + Integer.toHexString(decimal));
        System.out.println("RES: " + res);


        return "redirect:/converter";
    }

    public static String convertToBase(int decimalNumber, int targetBase) {
        StringBuilder result = new StringBuilder();
        while (decimalNumber > 0) {
            int remainder = decimalNumber % targetBase;
            System.out.println(decimalNumber + " " + remainder + " " + targetBase);
            System.out.println(String.valueOf((char) ('a' + remainder - 10)));
            result.insert(0, remainder > 9 ? String.valueOf((char) ('a' + remainder - 10)) : remainder);
            decimalNumber /= targetBase;
        }
        return result.toString();
    }

    public static int convertToDecimal(String number, int sourceBase) {
        int decimalNumber = 0;
        int power = 0;
        for (int i = number.length() - 1; i >= 0; i -= 1) {
            int digit = Character.getNumericValue(number.charAt(i));
            decimalNumber += (int) (digit * Math.pow(sourceBase, power));
            power++;
        }
        return decimalNumber;
    }
}
