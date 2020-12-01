package com.bros.hash;

public class BloomFilter {

    //总的bitmap大小  64M
    private static final int cap = 1 << 29;
    /*
     * 不同哈希函数的种子，一般取质数
     * seeds数组共有8个值，则代表采用8种不同的哈希函数
     */
    private int[] seeds = new int[]{3, 5, 7, 11, 13, 31, 37, 61};

    private int hash(String value, int seed) {
        int result = 0;
        int length = value.length();
        for (int i = 0; i < length; i++)    {
            result = seed * result + value.charAt(i);
        }
        return (cap - 1) & result;
    }

}
