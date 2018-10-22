package org.lium.algorithm.other;

public class ReverseInteger {
    public static void main(String[] args) {
        ReverseInteger obj = new ReverseInteger();
        int temp = obj.reverse(-10);
        System.out.println(temp);
    }

    public int reverse(int x) {

        // 保存返回结果
        int result = 0;

        // 终结条件，x为0
        while( x != 0 ) {
            // 之前得result进位
            // 然后 每次取x得个数位，并添加到result
            result = result * 10 + x % 10;

            // 更新x
            x = x / 10;

            // 下面处理特殊情况
            // 特殊情况1：result溢出
            if ( result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ) {
                return 0;
            }
        }
        return result;
    }
}
