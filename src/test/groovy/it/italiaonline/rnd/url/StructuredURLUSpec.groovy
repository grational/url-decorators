package it.italiaonline.rnd.url

import spock.lang.*

class StructuredURLUSpec extends Specification {

	@Unroll
	def "Should raise a IllegalArgumentException if one of the required keys is null"() {
		when:
			def urlObj = new StructuredURL (
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			def exception = thrown(IllegalArgumentException)
			exception.message == message
		where:
			base                   | path           | qparams                || message
			null                   | 'web-services' | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid base parameter'
			'http://www.polito.it' | null           | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid path parameter'
	}

	@Unroll
	def "Should raise a IllegalArgumentException if one of the keys or the value is null"() {
		when:
			def urlObj = new StructuredURL (
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			def exception = thrown(IllegalArgumentException)
			exception.message == message
		where:
			base                   | path           | qparams                    || message
			'http://www.polito.it' | 'web-services' | [ (null): 'p1', p2: 'p2' ] || "[StructuredURL] Invalid qparam key 'null'"
			'http://www.polito.it' | 'web-services' | [ p1: null, p2: 'p2' ]     || "[StructuredURL] Invalid qparam value 'null'"
	}

	@Unroll
	def "Should return a URI rapresentation of the url with query parameters"() {
		when:
			def urlObj = new StructuredURL (
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			expectedURI == urlObj.toURI()
		where:
			base                   | path           | qparams                || expectedURI
			'http://www.polito.it' | 'api'          | [:]                    || 'http://www.polito.it/api'.toURI()
			'http://www.polito.it' | 'api'          | [ p1: 'p1', p2: 'p2' ] || 'http://www.polito.it/api?p1=p1&p2=p2'.toURI()
			'http://www.polito.it' | 'api'          | [ p1: 'p1']            || 'http://www.polito.it/api?p1=p1'.toURI()
	}

	@Unroll
	def "Should return a URL rapresentation of the url with query parameters"() {
		when:
			def urlObj = new StructuredURL (
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			expectedURL == urlObj.toURL()
		where:
			base                   | path           | qparams                || expectedURL
			'http://www.polito.it' | 'api'          | [:]                    || 'http://www.polito.it/api'.toURL()
			'http://www.polito.it' | 'api'          | [ p1: 'p1', p2: 'p2' ] || 'http://www.polito.it/api?p1=p1&p2=p2'.toURL()
			'http://www.polito.it' | 'api'          | [ p1: 'p1']            || 'http://www.polito.it/api?p1=p1'.toURL()
	}

	@Unroll
	def "Should return a string rapresentation of the url complete of query parameters"() {
		when:
			def url = new StructuredURL (
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			expectedString == url.toString()
		where:
			base                   | path           | qparams                || expectedString
			'http://www.polito.it' | 'api'          | [:]                    || 'http://www.polito.it/api'
			'http://www.polito.it' | 'api'          | [ p1: 'p1', p2: 'p2' ] || 'http://www.polito.it/api?p1=p1&p2=p2'
			'http://www.polito.it' | 'api'          | [ p1: 'p1']            || 'http://www.polito.it/api?p1=p1'
	}
}
