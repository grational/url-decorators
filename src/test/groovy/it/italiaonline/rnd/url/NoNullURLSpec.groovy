package it.italiaonline.rnd.url

import spock.lang.Specification

class NoNullURLSpec extends Specification {

	def "should throw exception when the required arg is null"()
	throws IllegalArgumentException {
		when:
			new NoNullURL(null).toURL()

		then:
			final IllegalArgumentException exception = thrown()
			// Alternate syntax: def exception = thrown(ArticleNotFoundException)
			exception.message == "Input is NULL: can't go ahead."
	}

	def "Should NOT throw an exception where the required arg is provided"() {
		setup:
			URL url = new URL('https://www.google.it')
		when:
			URL sameURL = new NoNullURL(url).toURL()
		then:
			final IllegalArgumentException exception = notThrown()
			url == sameURL
	}

}
