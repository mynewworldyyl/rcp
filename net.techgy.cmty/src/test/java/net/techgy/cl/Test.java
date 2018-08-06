package net.techgy.cl;

import java.util.Date;

public class Test extends Date {
	public static void main(String[] args) {

		System.out.println(test());
	}

	static int test() {
		int x = 1;
		try {
			return 1;
		} finally {
			return 2;
		}
	}
}