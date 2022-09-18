/**
 * 生成统一格式（例如XP2021040200001）订单号工具类
 * @Author cht
 * @Date 2021/4/2 11:09:38
 */
package com.fukwang.util;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumGenerate {
    private static final String SERIAL_NUMBER = "XXXXXX"; // 流水号格式
    private static OrderNumGenerate orderNumGenerater = null;

    private OrderNumGenerate() {
    }
    /**
     * 取得PrimaryGenerate的单例实现
     * @return
     */
    public static OrderNumGenerate getInstance() {
        if (orderNumGenerater == null) {
            synchronized (OrderNumGenerate.class) {
                if (orderNumGenerater == null) {
                    orderNumGenerater = new OrderNumGenerate();
                }
            }
        }
        return orderNumGenerater;
    }
    /**
     * 生成下一个编号
     */
    public static synchronized String generateNextNumber(String num) {
        String id = null;
        String s = num.substring(0, 2); //截取订单号前缀
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        if (num == null) {
            id = s+formatter.format(date) + "000001";
        } else {
            int count = SERIAL_NUMBER.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            String dateString=num.substring(2, 10);
            if(formatter.format(date).compareTo(dateString)==0){
                DecimalFormat df = new DecimalFormat("000000");
                id =s+formatter.format(date)+ df.format(1 + Integer.parseInt(num.substring(10, 16)));
            }else{
                id = s+formatter.format(date) + "000001";
            }
        }
        return id;
    }
/*    public static void main(String[] args) {
        String no = "XP20210407000001";
        no = OrderNumGenerate.getInstance().generateNextNumber(no);
        System.out.println(no);
    }*/
}

