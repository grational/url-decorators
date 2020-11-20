package it.italiaonline.rnd.url

import groovy.transform.ToString

final class StructuredURL implements URLConvertible {
	private final String base
	private final String path
	private final String qstring

	StructuredURL(Map params) {
		this.base = params.base ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid base parameter") }()
		this.path = params.path ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid path parameter") }()

		def qparams  = params.qparams ?: ""
		this.qstring = qparams.inject('') { s, k, v ->
			def key   = k ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid qparam key '${k}'") }()
			def value = v ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid qparam value '${v}'") }()
			"${s}${s ? '&' : '?'}${key}=${value}"
		}
	}

	@Override
	URL toURL() {
		this.toString().toURL()
	}

	@Override
	URI toURI() {
		this.toString().toURI()
	}

	@Override
	String toString() {
		"${base}/${path}${qstring}".toString()
	}
}
