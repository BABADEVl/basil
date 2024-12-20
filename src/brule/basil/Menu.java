package brule.basil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final JFrame frame = new JFrame("Gestion des Smartphones");
    private final JPanel panel = new JPanel();
    private final List<SmartPhone> smartphones = new ArrayList<>();
    private final GestionFichier gestionFichier = new GestionFichier("smartphones.txt");

    public Menu() {
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chargerSmartphonesDepuisFichier();
    }

    public void mainMenu() {
        panel.setLayout(new GridLayout(6, 1));
        frame.add(panel);

        JButton ajout = new JButton("Ajouter un smartphone");
        JButton affichage = new JButton("Afficher les smartphones");
        JButton suppression = new JButton("Supprimer un smartphone");
        JButton quitter = new JButton("Quitter");

        panel.add(ajout);
        panel.add(affichage);
        panel.add(suppression);
        panel.add(quitter);

        ajout.addActionListener(e -> ajouterSmartphone());
        affichage.addActionListener(e -> afficherSmartphones());
        suppression.addActionListener(e -> supprimerSmartphone());
        quitter.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void ajouterSmartphone() {
        String marque = JOptionPane.showInputDialog(frame, "Entrez la marque du smartphone :");
        String numeroSerie = JOptionPane.showInputDialog(frame, "Entrez le numéro de série du smartphone :");

        if (marque != null && numeroSerie != null && !marque.isEmpty() && !numeroSerie.isEmpty()) {
            SmartPhone smartphone = new SmartPhone(marque, numeroSerie);
            smartphones.add(smartphone);
            gestionFichier.writeLine(marque + "," + numeroSerie);
            JOptionPane.showMessageDialog(frame, "Smartphone ajouté avec succès !");
        } else {
            JOptionPane.showMessageDialog(frame, "Les informations sont invalides.");
        }
    }

    private void afficherSmartphones() {
        if (smartphones.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucun smartphone dans la liste.");
            return;
        }

        String[] columns = {"Marque", "Numéro de Série"};
        Object[][] data = new Object[smartphones.size()][2];
        for (int i = 0; i < smartphones.size(); i++) {
            data[i][0] = smartphones.get(i).getMarque();
            data[i][1] = smartphones.get(i).getNumeroSerie();
        }

        JTable table = new JTable(data, columns);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> retournerMenuPrincipal());
        panel.removeAll();  
        panel.setLayout(new BorderLayout());  
        panel.add(scrollPane, BorderLayout.CENTER);  
        panel.add(retourButton, BorderLayout.SOUTH); 

        panel.revalidate(); 
        panel.repaint();
    }

    private void retournerMenuPrincipal() {
        panel.removeAll();
        mainMenu(); 
        panel.revalidate();
        panel.repaint();
    }

    private void supprimerSmartphone() {
        String numeroSerie = JOptionPane.showInputDialog(frame, "Entrez le numéro de série du smartphone à supprimer :");
        if (numeroSerie == null || numeroSerie.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Numéro de série invalide.");
            return;
        }

        SmartPhone aSupprimer = null;
        for (SmartPhone smartphone : smartphones) {
            if (smartphone.getNumeroSerie().equals(numeroSerie)) {
                aSupprimer = smartphone;
                break;
            }
        }

        if (aSupprimer != null) {
            smartphones.remove(aSupprimer);
            sauvegarderListeDansFichier();
            JOptionPane.showMessageDialog(frame, "Smartphone supprimé avec succès !");
        } else {
            JOptionPane.showMessageDialog(frame, "Aucun smartphone trouvé avec ce numéro de série.");
        }
    }

    private void chargerSmartphonesDepuisFichier() {
        List<String> lines = gestionFichier.readAllLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                smartphones.add(new SmartPhone(parts[0], parts[1]));
            }
        }
    }

    private void sauvegarderListeDansFichier() {
        List<String> lines = new ArrayList<>();
        for (SmartPhone smartphone : smartphones) {
            lines.add(smartphone.getMarque() + "," + smartphone.getNumeroSerie());
        }
        gestionFichier.writeAllLines(lines);
    }
}
