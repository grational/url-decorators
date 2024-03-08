package it.grational.url

import spock.lang.*

class StructuredURLUSpec extends Specification {

	@Unroll
	def "Should raise an IllegalArgumentException if one of the required keys is null"() {
		when:
			def urlObj = new StructuredURL (
				protocol:  protocol,
				authority: authority,
				path:      path,
				qparams:   qparams
			)
		then:
			def exception = thrown(IllegalArgumentException)
			exception.message == message
		where:
			protocol | authority            | path           | qparams                || message
			null     | 'www.polito.it:8080' | 'web-services' | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid protocol parameter'
			''       | 'www.polito.it:8080' | 'web-services' | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid protocol parameter'
			'http'   | null                 | 'web-services' | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid authority parameter'
			'http'   | ''                   | 'web-services' | [ p1: 'p1', p2: 'p2' ] || '[StructuredURL] Invalid authority parameter'
	}

	@Unroll
	def "Should take the origin parameter if available in place of protocol and authority"() {
		when:
			def urlObj = new StructuredURL (
				origin: origin,
				protocol:  protocol,
				authority: authority,
				path: path,
				qparams: qparams
			)
		then:
			expectedStringURL         == urlObj.toString()
			expectedStringURL.toURL() == urlObj.toURL()
			expectedStringURL.toURI() == urlObj.toURI()
		where:
			origin                   | protocol | authority        | path   | qparams                || expectedStringURL
			'http://domain.ext:1234' | 'http'   | 'polito.it:8080' | null   | null                   || 'http://domain.ext:1234'
			'http://domain.ext:1234' | null     | null             | '/api' | [ p1: 'p1', p2: 'p2' ] || 'http://domain.ext:1234/api?p1=p1&p2=p2'
			null                     | 'http'   | 'polito.it:8080' | '/api' | [ p1: 'p1', p2: 'p2' ] || 'http://polito.it:8080/api?p1=p1&p2=p2'
			''                       | 'http'   | 'polito.it:8080' | '/api' | [ p1: 'p1', p2: 'p2' ] || 'http://polito.it:8080/api?p1=p1&p2=p2'
	}

	@Unroll
	def "Should raise a IllegalArgumentException if one of the keys or the value is null"() {
		when:
			def urlObj = new StructuredURL (
				protocol:  protocol,
				authority: authority,
				path:      path,
				qparams:   qparams
			)
		then:
			def exception = thrown(IllegalArgumentException)
			exception.message == message
		where:
			protocol | authority            | path           | qparams                    || message
			'http'   | 'www.polito.it:8080' | 'web-services' | [ (null): 'p1', p2: 'p2' ] || "[StructuredURL] Invalid qparam key 'null'"
			'http'   | 'www.polito.it:8080' | 'web-services' | [ p1: null, p2: 'p2' ]     || "[StructuredURL] Invalid qparam value 'null'"
	}

	@Unroll
	def "Should return the correct string, URL and URI representation of the structured url object"() {
		when:
			def urlObj = new StructuredURL (
				protocol:  protocol,
				username:  username,
				password:  password,
				authority: authority,
				path:      path,
				qparams:   qparams
			)
		then:
			expectedStringURL         == urlObj.toString()
			expectedStringURL.toURL() == urlObj.toURL()
			expectedStringURL.toURI() == urlObj.toURI()
		and:
		and:
		where:
			protocol | username | password | authority            | path   | qparams                || expectedStringURL
			'http'   | null     | null     | 'www.polito.it'      | null   | null                   || 'http://www.polito.it'
			'https'  | null     | null     | 'www.polito.it:8080' | 'api'  | null                   || 'https://www.polito.it:8080/api'
			'https'  | 'user'   | 'pass'   | 'www.polito.it:8080' | '/api' | null                   || 'https://user:pass@www.polito.it:8080/api'
			'https'  | 'user'   | 'pass'   | 'www.polito.it:8080' | '/api' | [ p1: 'p1', p2: 'p2' ] || 'https://user:pass@www.polito.it:8080/api?p1=p1&p2=p2'
	}

}
