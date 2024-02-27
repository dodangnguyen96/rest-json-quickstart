package org.training.table;

public enum Status {
	Alive, Death;

	public static Status StatusRandom() {
		return values()[(int) (Math.random() * (values().length))];
	}
}
