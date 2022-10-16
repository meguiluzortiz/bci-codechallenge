package com.example.bci;

import com.example.bci.web.controller.SignupController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BciApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void shouldLoadContextWhenApplicationHasStarted() {
		BciApplication.main(new String[] {});

		SignupController bean = context.getBean(SignupController.class);
		assertThat(bean).isNotNull();
	}

}
