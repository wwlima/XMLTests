package br.com.wwlima.xmltests.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLUtils {
	public static StringWriter marshal(Object object) throws JAXBException {
		final StringWriter out = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(object, new StreamResult(out));
		return out;
	}

	public static String marshalToFile(Object object, String fileName, boolean override)
			throws JAXBException, IOException {
		File arq = new File(fileName);
		if (!arq.exists() || arq.exists() && override) {
			try (Writer writer = marshal(object);
					FileWriter fw = new FileWriter(arq);
					BufferedWriter br = new BufferedWriter(fw);) {
				br.write(writer.toString());
				return writer.toString();
			}
		} else {
			System.out.println("O arquivo " + fileName + " já existe e foi mantido sem alterações.");
			return "";
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshal(Class clazz, String stringXml) {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(new StreamSource(new StringReader(stringXml)));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshalFromFile(Class clazz, String fileXml) {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(new FileInputStream(fileXml));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validator(String xsdFile, String xmlPath) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdFile));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (IOException | SAXException e) {
			System.out.println("Erro ao validade o xml: " + xmlPath + " contra o XSL " + xsdFile);
			System.out.println("Erro: " + e.getMessage());
			return false;
		}
		return true;
	}
}
