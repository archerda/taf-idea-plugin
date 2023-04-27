package com.archerda.idea.plugin.taf.constant;

public interface Attribute {

    /**
     * '@RequestMapping' 注解的属性
     */
    interface RequestMapping {
        /**
         * Assign a name to this mapping.(给这个mapping分配一个名称，没有实质性的作用)
         */
        String NAME = "name";
        /**
         * 请求地址
         */
        String VALUE = "value";
        /**
         * 请求地址
         */
        String PATH = "path";
        /**
         * 请求类型，如 Get Post 等
         */
        String METHOD = "method";
    }

    /**
     * '@FeignClient' 注解的属性
     */
    interface FeignClient {
        /**
         * Feign 接口的实现所在模块名
         */
        String NAME = "name";
        /**
         * 同上
         */
        String VALUE = "value";
        /**
         * 该 Feign 接口下所有方法的请求 url 都要添加上 path 才是真正的访问路径
         */
        String PATH = "path";
        /**
         * 熔断回调
         */
        String FALLBACK = "fallback";
    }

}
