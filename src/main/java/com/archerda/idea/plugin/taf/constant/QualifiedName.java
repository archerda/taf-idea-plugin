package com.archerda.idea.plugin.taf.constant;

public interface QualifiedName {

    interface Annotation {
        /**
         * '@RequestMapping' 注解
         */
        String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";
        /**
         * '@PostMapping' 注解
         */
        String POST_MAPPING = "org.springframework.web.bind.annotation.PostMapping";
        /**
         * '@GetMapping' 注解
         */
        String GET_MAPPING = "org.springframework.web.bind.annotation.GetMapping";
        /**
         * '@PutMapping' 注解
         */
        String PUT_MAPPING = "org.springframework.web.bind.annotation.PutMapping";
        /**
         * '@DeleteMapping' 注解
         */
        String DELETE_MAPPING = "org.springframework.web.bind.annotation.DeleteMapping";
        /**
         * '@FeignClient' 注解
         */
        String FEIGN_CLIENT = "org.springframework.cloud.openfeign.FeignClient";
        /**
         * '@RestController' 注解
         */
        String REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";
        /**
         * '@Controller' 注解
         */
        String CONTROLLER = "org.springframework.stereotype.Controller";
        /**
         * '@Repository' 注解
         */
        String REPOSITORY = "org.springframework.stereotype.Repository";
        /**
         * MyBatis 的 @Mapper 注解
         */
        String MAPPER = "org.apache.ibatis.annotations.Mapper";
        /**
         * TAF的@Servant注解
         */
        String SERVANT = "com.huya.taf.protocol.annotation.Servant";

        /**
         * TAF的@TafServant注解
         */
        String TAF_SERVANT = "com.huya.taf.spring.annotation.TafServant";

    }


}
