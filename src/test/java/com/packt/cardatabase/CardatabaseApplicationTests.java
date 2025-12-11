package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.packt.cardatabase.controller.CarRestController;

@SpringBootTest
class CardatabaseApplicationTests {

    @Autowired
    private CarRestController controller;

    @Test
    @DisplayName("Test that controller is created and injected")
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}





