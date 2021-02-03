package com.calc;

import static org.assertj.core.api.Assertions.assertThat;

import com.calc.controller.RestCalcController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalcApplicationTests {

	@Autowired
	private RestCalcController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
