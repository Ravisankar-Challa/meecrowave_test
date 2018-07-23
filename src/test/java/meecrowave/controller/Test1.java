package meecrowave.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.ValueSource;

class Test1 {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Disabled
	void test() {
		fail("Not yet implemented");
	}
	
	@ParameterizedTest
	@CsvSource(
		{"1,2,4",
		 "3,3,6"}
	)
	@DisplayName("Test addition of two numbers")
	void test1(int a, int b, int result) {
		Hello h = new Hello();
		assertThat(h.add(a, b), equalTo(result));
	}
	
	@DisplayName("Enum Test")
	@ParameterizedTest
	@EnumSource(value = TimeUnit.class, mode = Mode.EXCLUDE, names = {"DAYS", "HOURS"})
	void test2(TimeUnit param) {
		assertTrue(param.toString().contains("E"));
	}
	
	@ParameterizedTest
	@DisplayName("Method Parameters")
	@MethodSource("getParams")
	void test3(String input) {
		assertTrue(input.matches("[A-Za-z]+"));
	}
	
	static Stream<String> getParams() {
		return Stream.of("JUNIT", "Java", "Java1");
	}
	
	@ParameterizedTest
	@DisplayName("String value parameters")
	@ValueSource(strings = {"JAVA", "Java", "Java1"})
	void test4(String input) {
		assertTrue(input.matches("[A-Za-z]+"));
	}
}

class Hello {
	public int add(int a, int b) {
		return a + b;
	}
}
