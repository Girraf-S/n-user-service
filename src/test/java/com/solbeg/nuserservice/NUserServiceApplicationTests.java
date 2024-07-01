package com.solbeg.nuserservice;

import com.solbeg.nuserservice.model.AuthParamsModel;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NUserServiceApplicationTests {

    private static String model(@Valid AuthParamsModel authParamsModel){
        return authParamsModel.toString();
    }
    @Test
    void contextLoads() {
        System.out.println(model(new AuthParamsModel("dsd", "ds")));
    }

}
