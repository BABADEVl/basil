package brule.basil;

import javax.swing.JFrame;

class SmartPhone extends JFrame {

   private String marque;
   private String numeroSerie;
   
   public SmartPhone(String marque, String numeroSerie) {
	   this.marque = marque;
	   this.numeroSerie = numeroSerie;
   }
    
    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }
    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    @Override
    public String toString() {
        return "Ordinateur [Marque=" + marque + ", Numéro de série=" + numeroSerie + "]";
    }
}