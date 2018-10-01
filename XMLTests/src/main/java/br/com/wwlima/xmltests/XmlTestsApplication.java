package br.com.wwlima.xmltests;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import br.com.wwlima.xmltests.exemplos.xsd.Employee;
import br.com.wwlima.xmltests.exemplos.xsd.Employee.Address;
import br.com.wwlima.xmltests.exemplos.xsd.ObjectFactory;
import br.com.wwlima.xmltests.utils.XMLUtils;

@SpringBootApplication
public class XmlTestsApplication {

	public static void main(String[] args) throws JAXBException, IOException, SAXException {
		// SpringApplication.run(XmlTestsApplication.class, args);
		ObjectFactory factory = new ObjectFactory();
		Employee emp = factory.createEmployee();
		emp.setId((byte) 1);
		emp.setName("William");

		Address endereco = factory.createEmployeeAddress();
		endereco.setAddressLine1("Rua Z");
		endereco.setAddressLine2("Cond Y");
		endereco.setCountry("Brasil");
		endereco.setState("Sergipe");
		endereco.setZip((short) 49000000);
		emp.setAddress(endereco);

		
		String xml = XMLUtils.marshalToFile(emp, "teste.xml", false);
		System.out.println(xml);

//		System.out.println("Xml valido: " + XMLUtils.validator("src/main/resources/XSD/exemplo2.xsd", "teste.xml"));
//		Employee employee = (Employee) XMLUtils.unmarshalFromFile(Employee.class, "teste.xml");
//		System.out.println(employee);

	}

}
