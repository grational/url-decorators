package it.italiaonline.rnd.url

import spock.lang.Specification
import spock.lang.Unroll

class UrlSpec extends Specification {

	def "Should raise a NullPointerException if the map parameter is null"() {
		when:
			def url = new Url()
		then:
			def exception = thrown(NullPointerException)
			exception.message == "parameters map can't be null"
	}

	@Unroll
	def "Should raise a NullPointerException if one of the required keys is null"() {
		when:
			def urlObj = new Url(
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			def exception = thrown(NullPointerException)
			exception.message == message
		where:
			base                   | path           | qparams                || message
			null                   | 'web-services' | [ p1: 'p1', p2: 'p2' ] || 'base url string is required'
			'http://www.polito.it' | null           | [ p1: 'p1', p2: 'p2' ] || 'path string is required'
			'http://www.polito.it' | 'web-services' | null                   || 'qparams are required'
	}

	@Unroll
	def "Should raise a NullPointerException if one of the keys or the value is null"() {
		when:
			def urlObj = new Url(
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			def exception = thrown(NullPointerException)
			exception.message == message
		where:
			base                   | path           | qparams                    || message
			'http://www.polito.it' | 'web-services' | [ (null): 'p1', p2: 'p2' ] || 'qparam keys must be not null'
			'http://www.polito.it' | 'web-services' | [ p1: null, p2: 'p2' ]     || 'qparam values must be not null'
	}

	@Unroll
	def "Should return a URI rapresentation of the url with query parameters"() {
		when:
			def urlObj = new Url(
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			expectedUri == urlObj.uri()
		where:
			base                   | path           | qparams                || expectedUri
			'http://www.polito.it' | 'api'          | [:]                    || 'http://www.polito.it/api'.toURI()
			'http://www.polito.it' | 'api'          | [ p1: 'p1', p2: 'p2' ] || 'http://www.polito.it/api?p1=p1&p2=p2'.toURI()
			'http://www.polito.it' | 'api'          | [ p1: 'p1']            || 'http://www.polito.it/api?p1=p1'.toURI()
	}

	@Unroll
	def "Should return a URL rapresentation of the url with query parameters"() {
		when:
			def urlObj = new Url(
				base:    base,
				path:    path,
				qparams: qparams
			)
		then:
			expectedUrl == urlObj.url()
		where:
			base                   | path           | qparams                || expectedUrl
			'http://www.polito.it' | 'api'          | [:]                    || 'http://www.polito.it/api'.toURL()
			'http://www.polito.it' | 'api'          | [ p1: 'p1', p2: 'p2' ] || 'http://www.polito.it/api?p1=p1&p2=p2'.toURL()
			'http://www.polito.it' | 'api'          | [ p1: 'p1']            || 'http://www.polito.it/api?p1=p1'.toURL()
	}

	@Unroll
	def "Should return a string rapresentation of the url complete of query parameters"() {
		when:
			def url = new Url(
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
