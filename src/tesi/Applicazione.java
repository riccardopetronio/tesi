package tesi;


public class Applicazione {
    public static void main(String[] args) {

    	GestoreComandiUtente comandi = new GestoreComandiUtente();
        //VirtualMachineService service = new VirtualMachineService();

        //service.salvaVirMacDaAzureAlDB("tesi-petronio"); // fornisco solo il nome del resorce group
        
        comandi.primaScelta();

    }
}