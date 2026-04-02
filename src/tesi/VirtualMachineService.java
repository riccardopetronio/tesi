package tesi;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.TreeMap;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;

public class VirtualMachineService {
	
	
	public static void salvaVirMacDaAzureAlDB(String resourceGroupName) {

	    System.out.println("Inizio sincronizazione a Azure e al DB...");

	    AzureResourceManager manager = AzureConnector.getManager();	    
        
	    for (VirtualMachine vm : manager.virtualMachines().listByResourceGroup(resourceGroupName)) {

	        System.out.println("Salvataggio VM: " + vm.name());

	        try {
	        	DAO_VirtualMachines.aggiungiVM(vm);
	        } catch (SQLException e) {
	            System.err.println("Errore salvataggio VM: "+ e + "\n");
	        }
	    }

	    System.out.println("Fine sincronizzazione");
	}
	
	public static TreeMap<String, VMRecord> getAllVMDalDB(){
		try {
			return DAO_VirtualMachines.getAllVM();
		} catch (SQLException e) {
		    System.err.println("errore durante l'ottenimento delle VM dal DB");
			return null;
		}
	}
	
	
	public static void sincronizzaVM(String resourceGroupName) throws SQLException {

        // Prendo tutte le VM dal database
		TreeMap<String, VMRecord> vmDb = DAO_VirtualMachines.getAllVM();

        // Set per tenere traccia delle VM viste su Azure, serve dopo per capire quali eliminare dal DB
        LinkedList<String> idVisti = new LinkedList<String>();

        AzureResourceManager manager = AzureConnector.getManager();

        // Ciclo su tutte le VM presenti nel resource group su Azure
        for (VirtualMachine vmAzure : manager.virtualMachines().listByResourceGroup(resourceGroupName)) {

            // Prendo i dati dalla VM Azure
            String id = vmAzure.vmId();    // chiave unica
            String nome = vmAzure.computerName();
            String os = vmAzure.osType().toString();
            String stato = vmAzure.powerState().toString();

            idVisti.add(id); // segno che questa VM esiste su Azure

            // Controllo se questa VM esiste già nel DB
            VMRecord recordDb = vmDb.get(id);

            if (recordDb == null) {
                // ❗ NON esiste nel DB → la aggiungo
                DAO_VirtualMachines.aggiungiVM(vmAzure);

            } else {
                // Esiste già → controllo se qualcosa è cambiato
                boolean cambiato =
                        !recordDb.getNome().equals(nome) ||
                        !recordDb.getOs().equals(os) ||
                        !recordDb.getStato().equals(stato);

                if (cambiato) {
                    // Esiste ma è diversa → aggiorno
                    DAO_VirtualMachines.aggiornaVM( new VMRecord(id, nome, os, stato) );
                }
            }
        }

        // Controllo se nel DB ci sono VM che NON esistono più su Azure
        for (VMRecord record : vmDb.values()) {
            if (!idVisti.contains(record.getId())) {
                // Esiste nel DB ma non su Azure → la elimino
                DAO_VirtualMachines.eliminaVM(record.getId());
            }
        }
    }
}
