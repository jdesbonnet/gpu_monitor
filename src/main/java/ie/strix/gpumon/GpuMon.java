package ie.strix.gpumon;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class GpuMon
{
	private static void usage () {
		System.out.println("parameter: xmlDataDir");
	}
	
    public static void main( String[] args ) throws Exception {
    	
    	if (args.length < 1) {
    		usage();
    		return;
    	}
    	
        File dataDir = new File(args[0]);
        
        File[] files = dataDir.listFiles();
        Arrays.sort(files);
        
        for (File xmlFile : files) {
        	if ( ! xmlFile.getName().endsWith(".xml")) {
        		continue;
        	}
        	
        	process(xmlFile);
        	
        		
        }
    }

	public static void process(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		String[] fncol = xmlFile.getName().substring(0,xmlFile.getName().length()-4).split("-");
		String ts = fncol[2];
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setFeature("http://xml.org/sax/features/namespaces", false);
		factory.setFeature("http://xml.org/sax/features/validation", false);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);

		XPathFactory xpathfactory = XPathFactory.newInstance();
		XPath xpath = xpathfactory.newXPath();

		NodeList gpuNodes = (NodeList) xpath.compile("//gpu").evaluate(doc, XPathConstants.NODESET);

		// System.out.println("found " + gpuNodes.getLength() + " GPUs");

		for (int i = 0; i < 1; i++) {
			Node gpuNode = gpuNodes.item(i);
		
			String gpuUuid = (String) xpath.compile("//uuid/text()").evaluate(gpuNode, XPathConstants.STRING);

			String busRx = (String) xpath.compile("//pci/rx_util/text()").evaluate(gpuNode, XPathConstants.STRING);
			String busTx = (String) xpath.compile("//pci/tx_util/text()").evaluate(gpuNode, XPathConstants.STRING);
			String gpuUtil = (String) xpath.compile("//utilization/gpu_util/text()").evaluate(gpuNode, XPathConstants.STRING);
			String memUtil = (String) xpath.compile("//utilization/mem_util/text()").evaluate(gpuNode, XPathConstants.STRING);
			String gpuTemp = (String) xpath.compile("//temperature/gpu_temp/text()").evaluate(gpuNode, XPathConstants.STRING);
			String fanSpeed = (String) xpath.compile("//fan_speed/text()").evaluate(gpuNode, XPathConstants.STRING);
			String power = (String) xpath.compile("//power_readings/power_draw/text()").evaluate(gpuNode, XPathConstants.STRING);

			System.out.println(ts 
				+ " " + gpuUuid
				+ " " + stripUnit(gpuUtil) + " " + stripUnit(memUtil)
				+ " " + stripUnit(busRx) + " " + stripUnit(busTx) 
				+ " " + stripUnit(gpuTemp) + " " + stripUnit(fanSpeed)
				+ " " + stripUnit(power)
				);
		}

	}
    
	private static String stripUnit (String meas) {
		String[] col = meas.split(" ");
		return col[0];
	}
	
    private static int toKBps (String rxtx) {
    	String[] col = rxtx.split(" ");
    	return Integer.parseInt(col[0]);
    }
}
