import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        // 1- Select molecules with the lowest bond strength from each structure
        List<Molecule> humanCandidates = selectCandidates(humanStructures);
        List<Molecule> vitalesCandidates = selectCandidates(diffStructures);

        // 2- Calculate potential bond strengths between all pairs of candidates
        List<Bond> potentialBonds = calculatePotentialBonds(humanCandidates, vitalesCandidates);

        // 3- Sort potential bonds by strength in ascending order
        potentialBonds.sort(Comparator.comparingDouble(Bond::getWeight));

        // 4- Build the serum by selecting the minimum bonds needed to connect all structures
        Set<MolecularStructure> connectedStructures = new HashSet<>();
        for (Bond bond : potentialBonds) {
            MolecularStructure structure1 = findStructureForMolecule(bond.getFrom(), humanStructures, diffStructures);
            MolecularStructure structure2 = findStructureForMolecule(bond.getTo(), humanStructures, diffStructures);
    
            if (structure1 != null && structure2 != null && !structure1.equals(structure2)) {
                // Add the bond if at least one of the structures isn't fully connected yet
                if (!isConnected(structure1, connectedStructures) || !isConnected(structure2, connectedStructures)) {
                    serum.add(bond);
                    connectedStructures.add(structure1);
                    connectedStructures.add(structure2);
                } 
            }
    
            if (connectedStructures.size() == humanStructures.size() + diffStructures.size()) {
                break; // All structures connected
            }
        }

        return serum;
    }

    // Helper function to check if a structure is connected (has at least one bond)
    private boolean isConnected(MolecularStructure structure, Set<MolecularStructure> connectedStructures) {
        for (MolecularStructure connectedStructure : connectedStructures) {
            if (connectedStructure.equals(structure)) {
                return true;
            }
        }
        return false;
    }

    // Helper function to select candidate molecules with the lowest bond strength
    private List<Molecule> selectCandidates(List<MolecularStructure> structures) {
        List<Molecule> candidates = new ArrayList<>();
        for (MolecularStructure structure : structures) {
            candidates.add(structure.getMoleculeWithWeakestBondStrength());
        }
        return candidates;
    } 

    // Helper function to calculate potential bond strengths
    private List<Bond> calculatePotentialBonds(List<Molecule> humanCandidates, List<Molecule> vitalesCandidates) {
        List<Bond> potentialBonds = new ArrayList<>();
        List<Molecule> allCandidates = new ArrayList<>();
        allCandidates.addAll(humanCandidates);
        allCandidates.addAll(vitalesCandidates);

        for (Molecule molecule1 : allCandidates) {
            for (Molecule molecule2 : allCandidates) {
                if (!molecule1.equals(molecule2)) {
                    double bondStrength = (molecule1.getBondStrength() + molecule2.getBondStrength()) / 2.0;
                    // Add a potential bond between the two molecules, bond's to is the one with the lowest id
                    if (molecule1.compareTo(molecule2) < 0) {
                        potentialBonds.add(new Bond(molecule2, molecule1, bondStrength));
                    } else {
                        potentialBonds.add(new Bond(molecule1, molecule2, bondStrength));
                    }
                }
            }
        }

        return potentialBonds;
    }

    // Helper function to find the structure containing a molecule
    private MolecularStructure findStructureForMolecule(Molecule molecule, List<MolecularStructure> humanStructures, List<MolecularStructure> vitalesStructures) {
        for (MolecularStructure structure : humanStructures) {
            if (structure.hasMolecule(molecule.getId())) {
                return structure;
            }
        }
        for (MolecularStructure structure : vitalesStructures) {
            if (structure.hasMolecule(molecule.getId())) {
                return structure;
            }
        }
        return null;
    }

    // Helper function to check if a molecule is part of a human structure
    private Boolean isHumanMolecule(Molecule molecule) {
        for (MolecularStructure structure : humanStructures) {
            if (structure.hasMolecule(molecule.getId())) {
                return true;
            }
        }
        return false;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        // Print selected molecules for synthesis
        HashSet<String> humanMolecules = new HashSet<>();
        HashSet<String> vitalesMolecules = new HashSet<>();
        HashSet<Molecule> allMolecules = new HashSet<>();
        for (Bond bond : serum) {
            allMolecules.add(bond.getFrom());
            allMolecules.add(bond.getTo());
        }

        for (Molecule molecule : allMolecules) {
            if (isHumanMolecule(molecule)) {
                humanMolecules.add(molecule.getId());
            } else {
                vitalesMolecules.add(molecule.getId());
            }
        }

        System.out.println("Typical human molecules selected for synthesis: " + humanMolecules);
        System.out.println("Vitales molecules selected for synthesis: " + vitalesMolecules);
        
        System.out.println("Synthesizing the serum...");
        double totalBondStrength = 0.0;
        for (Bond bond : serum) {
            System.out.printf("Forming a bond between %s - %s with strength %.2f\n", bond.getFrom(), bond.getTo(), bond.getWeight());
            totalBondStrength += bond.getWeight();
        }
        System.out.printf("The total serum bond strength is %.2f\n", totalBondStrength);
    }
}
