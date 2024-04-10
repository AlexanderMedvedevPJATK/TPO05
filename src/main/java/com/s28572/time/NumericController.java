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
    @ResponseBody
    public String convert(@RequestParam(name = "value-to-convert") String value,
                          @RequestParam(name = "from-base") int fromBase,
                          @RequestParam(name = "to-base") int toBase) {
        try {
            int decimal = convertToDecimal(value, fromBase);
            String res = convertToBase(decimal, toBase);
            return String.format("""
                INIT VALUE: %s<br>
                RES: %s<br>
                BIN: %s<br>
                OCT: %s<br>
                DEC: %s<br>
                HEX: %s<br>
                """,
                    value, res, Integer.toBinaryString(decimal),
                    Integer.toOctalString(decimal), decimal, Integer.toHexString(decimal));
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public static String convertToBase(int decimalNumber, int targetBase) {
        StringBuilder result = new StringBuilder();
        while (decimalNumber > 0) {
            int remainder = decimalNumber % targetBase;
            result.insert(0, remainder > 9 ? String.valueOf((char) ('a' + remainder - 10)) : remainder);
            decimalNumber /= targetBase;
        }
        return result.toString();
    }

    public static int convertToDecimal(String number, int sourceBase) {
        if (number.startsWith("-")) {
            throw new IllegalArgumentException("Input value cannot be negative");
        }
        int decimalNumber = 0;
        int power = 0;
        for (int i = number.length() - 1; i >= 0; i -= 1) {
            char digitCh = number.charAt(i);
            int digit;
            if (digitCh >= '0' && digitCh <= '9') {
                digit = Character.getNumericValue(digitCh);
            } else {
                digit = number.charAt(i) - 87;
            }
            if (digit >= sourceBase) {
                System.out.println(digit);
                System.out.println(sourceBase - 1);
                throw new IllegalArgumentException("Number is not in specified base system");
            }
            System.out.println(digit);
            decimalNumber += (int) (digit * Math.pow(sourceBase, power));
            System.out.println(decimalNumber + " " + (digit * Math.pow(sourceBase, power)));
            power++;
        }
        return decimalNumber;
    }
}
