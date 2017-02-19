package it.italiaonline.rnd.url

class QParamsFilter {

	private final URL  url
	private final List tabu

	QParamsFilter(
		URL  url,
		List tabu
	) {
		this.url  = url
		this.tabu = tabu
	}

	String filtered() {
		def output = this.url.toString()
		this.tabu.each { qparam ->
			output = output.replaceAll(/${qparam}=[^&]*/,'')
			               .replaceAll(/&&/,'&')
			               .replaceAll(/[?]&/,'?')
			               .replaceAll(/[?]$/,'')
			               .replaceAll(/&$/,'')
		}
		return output
	}
}
