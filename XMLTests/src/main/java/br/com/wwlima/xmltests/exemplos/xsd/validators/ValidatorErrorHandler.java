package br.com.wwlima.xmltests.exemplos.xsd.validators;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidatorErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		arg0.printStackTrace();
	}

}
