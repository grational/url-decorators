package it.italiaonline.rnd.url

interface AuthURL {
	Boolean auth()
	String  username()
	String  password()
	String  header()
}
