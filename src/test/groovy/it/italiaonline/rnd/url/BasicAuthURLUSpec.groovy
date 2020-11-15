package it.italiaonline.rnd.url

import spock.lang.*

class BasicAuthURLUSpec extends Specification {

	@Unroll
	def "Should raise an IllegalArgumentException if the required parameter is null"() {
		when:
			new BasicAuthURL(invalidURLString)
		then:
			def exception = thrown(IllegalArgumentException)
			exception.message == "[BasicAuthURL] Invalid url string '${invalidURLString}'"
		where:
			invalidURLString << [
				null,
				'http:/www.polito.it'
			]
	}

	@Unroll
	def "Should be capable of parse every element of a URL with or without the basic authentication credentials"() {
		given:
			def basicAuthURL = new BasicAuthURL(url)
		expect: 'check the main sections of the URL'
			basicAuthURL.host() == host
			basicAuthURL.port() == port
			basicAuthURL.path() == path
		and: 'check the credentials if needed'
			basicAuthURL.auth() == auth
			if ( basicAuthURL.auth() ) {
				basicAuthURL.username() == username
				basicAuthURL.password() == password
			}

		where:
			url                                                                                  || host                          | port | path                    | auth  | username    | password
			'https://lead-user:Js_Adlò?à!$fhF@tst-leadsws.italiaonline.it/lead-queue/duda-leads' || 'tst-leadsws.italiaonline.it' | 443  | 'lead-queue/duda-leads' | true  | 'lead-user' | 'Js_Adlò?à!$fhF'
			'http://tst-dab-1.pgol.net:5556/v1/duda/event/form'                                  || 'tst-dab-1.pgol.net'          | 5556 | 'v1/duda/event/form'    | false | null        | null
			'http://tst-dab-1.pgol.net:5557/v1/duda/event/editor'                                || 'tst-dab-1.pgol.net'          | 5557 | 'v1/duda/event/editor'  | false | null        | null
	}

	@Unroll
	def "Should return a URI rapresentation of the url with query parameters"() {
		given:
			def basicAuthURL = new BasicAuthURL(input)
		expect:
			basicAuthURL.uri() == expected.toURI()
			basicAuthURL.url() == expected.toURL()
		where:
			input                                                                                  || expected
				'https://lead-user:Js_Adlò?à!$fhF@tst-leadsws.italiaonline.it/lead-queue/duda-leads' || 'https://tst-leadsws.italiaonline.it/lead-queue/duda-leads'
				'http://tst-dab-1.pgol.net:5556/v1/duda/event/form'                                  || 'http://tst-dab-1.pgol.net:5556/v1/duda/event/form'
				'http://tst-dab-1.pgol.net:5557/v1/duda/event/editor'                                || 'http://tst-dab-1.pgol.net:5557/v1/duda/event/editor'
	}

	@Unroll
	def "Should be capable of return the initial version of the url passes as a parameter"() {
		expect:
			new BasicAuthURL(input).toString() == input
		where:
			input << [
				'https://lead-user:Js_Adlò?à!$fhF@tst-leadsws.italiaonline.it/lead-queue/duda-leads',
				'http://tst-dab-1.pgol.net:5556/v1/duda/event/form'
			]
	}
}
// vim: ft=groovy:fdm=indent
