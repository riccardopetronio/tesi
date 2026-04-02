package tesi;

import java.sql.SQLException;

public class Applicazione {
    public static void main(String[] args) throws SQLException {

    	GestoreComandiUtente comandi = new GestoreComandiUtente();

        //VirtualMachineService.salvaVirMacDaAzureAlDB("tesi-petronio"); // fornisco solo il nome del resorce group
        
        comandi.primaScelta();

    }
}