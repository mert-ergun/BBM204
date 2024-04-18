// Class representing the mission of Genesis

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document; // Import the Document class
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File; // Import the File class
import java.util.ArrayList;
import java.util.List;

public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {
        // Parse the XML file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

         // Get Human and Vitales data nodes
         NodeList humanDataNodes = document.getElementsByTagName("HumanMolecularData");
         NodeList vitalesDataNodes = document.getElementsByTagName("VitalesMolecularData");
 
         // Extract and store molecule data for humans
         molecularDataHuman = extractMolecularData(humanDataNodes);
 
         // Extract and store molecule data for Vitales
         molecularDataVitales = extractMolecularData(vitalesDataNodes);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper function to extract molecule data from NodeList
    private MolecularData extractMolecularData(NodeList dataNodes) {
        List<Molecule> molecules = new ArrayList<>();

        // Iterate over each molecule node
        for (int i = 0; i < dataNodes.getLength(); i++) {
            Node moleculeNode = dataNodes.item(i).getFirstChild();
            while (moleculeNode != null) {
                if (moleculeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element moleculeElement = (Element) moleculeNode;

                    // Extract molecule information
                    String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());

                    // Extract bonded molecule IDs
                    List<String> bondedMoleculeIds = new ArrayList<>();
                    NodeList bondNodes = moleculeElement.getElementsByTagName("MoleculeID");
                    for (int j = 0; j < bondNodes.getLength(); j++) {
                        bondedMoleculeIds.add(bondNodes.item(j).getTextContent());
                    }

                    // Create Molecule object and add to list
                    molecules.add(new Molecule(id, bondStrength, bondedMoleculeIds));
                }
                moleculeNode = moleculeNode.getNextSibling();
            }
        }

        return new MolecularData(molecules);
    }
}
