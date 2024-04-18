import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        List<MolecularStructure> structures = new ArrayList<>();
        Set<String> visited = new HashSet<>(); // Keep track of visited molecules
    
        for (Molecule molecule : molecules) {
            if (!visited.contains(molecule.getId())) {
                depthFirstSearch(molecule, structures, visited); // Perform DFS to build the structure
            }
        }
    
        return structures;
    }
    

    // Helper function for depth-first search to build molecular structures
    private void depthFirstSearch(Molecule molecule, List<MolecularStructure> structures, Set<String> visited) {
        visited.add(molecule.getId());
        MolecularStructure structure = findStructureWithBondedMolecule(molecule, structures);

        if (structure == null) {
            structure = new MolecularStructure();
            structures.add(structure);
        }
        structure.addMolecule(molecule);

        for (String bondedId : molecule.getBonds()) {
            if (!visited.contains(bondedId)) {
                Molecule bondedMolecule = findMoleculeById(bondedId);
                if (bondedMolecule != null) {
                    depthFirstSearch(bondedMolecule, structures, visited);
                }
            }
        }
    }

    private MolecularStructure findStructureWithBondedMolecule(Molecule molecule, List<MolecularStructure> structures) {
        for (MolecularStructure structure : structures) {
            for (String bondedId : molecule.getBonds()) {
                if (structure.hasMolecule(bondedId)) {
                    return structure;
                }
            }
        }
        return null;
    }

    // Helper function to find a molecule by its ID
    private Molecule findMoleculeById(String id) {
        return molecules.stream()
                        .filter(m -> m.getId().equals(id))
                        .findFirst()
                        .orElse(null);
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        int structureCount = 1;
        for (MolecularStructure structure : molecularStructures) {
            System.out.print("Molecules in Molecular Structure " + structureCount + ": ");
            System.out.println(structure.toString());
            structureCount++; 
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        
        for (MolecularStructure targetStructure : targeStructures) {
            boolean isAnomaly = true;
            for (MolecularStructure sourceStructure : sourceStructures) {
                if (targetStructure.equals(sourceStructure)) {
                    isAnomaly = false;
                    break;
                }
            }
            if (isAnomaly) {
                anomalyList.add(targetStructure);
            }
        }

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure.toString());
        }


    }
}
