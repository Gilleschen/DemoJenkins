package Auto;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class Junit {

	private boolean result;

	public Junit(boolean result) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {

		this.result = result;

	}

	@Parameters
	public static Collection primeNumbers() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		method Method = new method();
		return Method.method();
	}

	@Test
	public void Case() {
		assertTrue(result);
	}
	// @Test
	// public void test() {
	// boolean result;
	// String[] s = { "true", "false" };
	// for (int i = 0; i < s.length; i++) {
	// switch (s[i]) {
	//
	// case "true":
	// result = new Boolean(s[i]).booleanValue();
	// break;
	// case "false":
	// result = new Boolean(s[i]).booleanValue();
	// break;
	// default:
	// result = false;
	// break;
	// }
	// assertTrue(result);
	// }
	//
	// }

}
