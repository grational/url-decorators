package it.italiaonline.rnd.url

import groovy.transform.ToString

final class Url {
	private final String base
	private final String path
	private final String qstring

	Url (Map m) {
		def nnm      = Objects.requireNonNull(m,"parameters map can't be null")
		this.base    = Objects.requireNonNull(nnm.base,'base url string is required')
		this.path    = Objects.requireNonNull(nnm.path,'path string is required')

		def qparams  = nnm.qparams ?: ""
		this.qstring = qparams.inject('') { s, k, v ->
			def key   = Objects.requireNonNull(k,'qparam keys must be not null')
			def value = Objects.requireNonNull(v,'qparam values must be not null')
			"${s}${s ? '&' : '?'}${key}=${value}"
		}
	}

	URI uri() {
		this.toString().toURI()
	}

	URL url() {
		this.toString().toURL()
	}

	@Override
	String toString() {
		"${base}/${path}${qstring}".toString()
	}
}
