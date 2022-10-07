package com.shf.myjuc2.cf;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 */
public class CompletableFutureMallDemo2 {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    /**
     * 一家一家查
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream()
                .map(netMall ->
                        String.format(productName + "\t in %s price is \t %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    /**
     * 万箭齐发
     * List<NetMall>----->List<CompletableFuture>----->List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> CompletableFuture.supplyAsync(() ->
                String.format(productName + "\t in %s price is \t %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream().map(s->s.join())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----endTime: " + (endTime - startTime) + "毫秒");

        System.out.println("-------------------------------------------------");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----endTime: " + (endTime2 - startTime2) + "毫秒");
    }
}


class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    @SneakyThrows
    public double calcPrice(String productName) {
        TimeUnit.SECONDS.sleep(1);

        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}