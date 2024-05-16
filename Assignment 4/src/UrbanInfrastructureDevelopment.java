import java.io.File;
import java.io.Serializable;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList) {
            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();

        try {
            // Create a DocumentBuilder Factory to create a DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Create a DocumentBuilder to parse the XML file
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filename));
            doc.getDocumentElement().normalize(); // Normalize the document to remove empty text nodes
            
            // Get all the Project nodes, and iterate over them
            NodeList nList = doc.getElementsByTagName("Project");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node pNode = nList.item(temp);
                if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pElement = (Element) pNode;
                    String name = pElement.getElementsByTagName("Name").item(0).getTextContent(); // Get the name of the project
                    NodeList taskList = pElement.getElementsByTagName("Task"); // Get all the Task nodes for the project
                    List<Task> tasks = new ArrayList<>();
                    // Iterate over all the Task nodes
                    for (int i = 0; i < taskList.getLength(); i++) {
                        Node tNode = taskList.item(i);
                        if (tNode.getNodeType() == Node.ELEMENT_NODE) { // Check if the node is an Element node and not a Text node
                            Element tElement = (Element) tNode;
                            int taskID = Integer.parseInt(tElement.getElementsByTagName("TaskID").item(0).getTextContent()); // Get the TaskID
                            String description = tElement.getElementsByTagName("Description").item(0).getTextContent(); // Get the Description
                            int duration = Integer.parseInt(tElement.getElementsByTagName("Duration").item(0).getTextContent()); // Get the Duration
                            NodeList depNodes = tElement.getElementsByTagName("DependsOnTaskID"); // Get all the DependsOnTaskID nodes
                            List<Integer> dependencies = new ArrayList<>();
                            for (int j = 0; j < depNodes.getLength(); j++) {
                                dependencies.add(Integer.parseInt(depNodes.item(j).getTextContent())); // Add the TaskID to the dependencies list as integer
                            }
                            tasks.add(new Task(taskID, description, duration, dependencies)); // Add the Task object to the tasks list
                        }
                    }
                    projectList.add(new Project(name, tasks));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }
}
