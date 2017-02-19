package it.italiaonline.rnd.url

final class NoNullURL {
	private final URL input

	NoNullURL(URL inpt) {
		this.input = inpt
	}

	URL toURL() throws IllegalArgumentException {
		if (input == null) {
			throw new IllegalArgumentException("Input is NULL: can't go ahead.")
		}
		this.input
	}

	@Override
	String toString() {
		this.input.toString()
	}
}
