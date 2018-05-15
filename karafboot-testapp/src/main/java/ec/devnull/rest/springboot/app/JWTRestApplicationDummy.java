/*
 * mcm-rest-client
 *
 * Copyright (c) 2018.  FISA Group.
 *
 * This software is the confidential and proprietary information FISA GROUP
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FISA Group.
 */

package ec.devnull.rest.springboot.app;


import ec.devnull.rest.springboot.app.config.APIAuthenticationManager;
import ec.devnull.rest.springboot.app.config.APISecurityConfig;
import ec.devnull.rest.springboot.app.config.BasicAuthenticationPoint;
import ec.devnull.rest.springboot.app.config.WebApplicationConfig;
import ec.devnull.rest.springboot.app.controller.OrderController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import({APIAuthenticationManager.class,
        APISecurityConfig.class,
        BasicAuthenticationPoint.class,
        WebApplicationConfig.class,
        TestPropertiesPostProcessor.class,
        OrderController.class,
        springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration.class,
        springfox.documentation.swagger2.mappers.ModelMapperImpl.class,
        springfox.documentation.swagger2.mappers.ParameterMapperImpl.class,
        springfox.documentation.swagger2.mappers.SecurityMapperImpl.class,
        springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl.class,
        springfox.documentation.swagger2.mappers.VendorExtensionsMapperImpl.class,
        springfox.documentation.swagger2.mappers.LicenseMapperImpl.class

})
public class JWTRestApplicationDummy {


    private ConfigurableApplicationContext ctx = null;

    public static void main(String[] args) {
     //   ConfigurableApplicationContext context = SpringApplication.run(JWTRestApplicationDummy.class, args);

//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }

    public void start() {
        String[] args = {};
        ctx = SpringApplication.run(JWTRestApplicationDummy.class, args);

//        for (String name : ctx.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }

    }

    public void stop() {
        if (ctx != null) {
            ctx.close();
        }
    }

}
