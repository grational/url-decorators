package it.grational.url

final class StructuredURL implements URLConvertible {
	private final String protocol
	private final String username
	private final String password
	private final String authority
	private final String path
	private final String qstring

	StructuredURL(Map params) {
		this.protocol = params.protocol ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid protocol parameter") }()

		this.username = params.username ?: ''
		this.password = params.password ?: ''

		this.authority = params.authority ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid authority parameter") }()

		this.path = params.path ?: ''

		this.qstring = params.qparams?.inject('') { s, k, v ->
			def key   = k ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid qparam key '${k}'") }()
			def value = v ?: { throw new IllegalArgumentException("[${this.class.simpleName}] Invalid qparam value '${v}'") }()
			"${s}${s ? '&' : '?'}${key}=${value}"
		} ?: ''

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
		println "protocol (${protocol.getClass()}) -> ${protocol}"
		println "username (${username.getClass()}) -> ${username}"
		println "password (${password.getClass()}) -> ${password}"
		println "authority (${authority.getClass()}) -> ${authority}"
		println "path (${path.getClass()}) -> ${path}"
		println "qstring (${qstring.getClass()}) -> ${qstring}"
		String result = "${protocol}://"
		if (username)
			result += "${username}:${password}@"
		result += authority
		if (path)
			result += path.startsWith('/') ? path : "/${path}"
		result += qstring

		println "result (${result.getClass()}) -> ${result}"

		return result
	}
}
