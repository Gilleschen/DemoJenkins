package Auto;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class Junit_Test {
	private boolean result;
	private String casename;

	public Junit_Test(String casename, boolean result) {
		this.casename = casename;
		this.result = result;
	}

	// @Parameters(name = "{index}. CaseName:{0}")
	@Parameters(name = "{index}. CaseName:{0}")
	public static Collection primeNumbers() {

		return Arrays.asList(new Object[][] { { "login", true }, { "test", "error" }, { "logout", false } });
	}

	@Test
	public void test() {
		assertTrue(result);
	}

}
