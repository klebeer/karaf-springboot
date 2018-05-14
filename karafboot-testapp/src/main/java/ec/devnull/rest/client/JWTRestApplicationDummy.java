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

package ec.devnull.rest.client;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import({ec.devnull.rest.client.config.APIAuthenticationManager.class,
        ec.devnull.rest.client.config.APISecurityConfig.class,
        ec.devnull.rest.client.config.BasicAuthenticationPoint.class,
        ec.devnull.rest.client.config.WebApplicationConfig.class,
        ec.devnull.rest.client.TestPropertiesPostProcessor.class,
        ec.devnull.rest.client.controller.OrderController.class,
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
