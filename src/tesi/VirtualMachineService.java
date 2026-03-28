package tesi;

import java.sql.SQLException;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;

public class VirtualMachineService {
	
	public void salvaVirMacDaAzureAlDB(String resourceGroupName) {

	    System.out.println("Inizio sincronizazione a Azure e al DB...");

	    AzureResourceManager manager = AzureConnector.getManager();
        DAO_VirtualMachines daoVM = new DAO_VirtualMachines();
	    
        
	    for (VirtualMachine vm : manager.virtualMachines().listByResourceGroup(resourceGroupName)) {

	        System.out.println("Salvataggio VM: " + vm.name());

	        try {
	            daoVM.aggiungiVM(vm);
	        } catch (SQLException e) {
	            System.err.println("Errore salvataggio VM: "+ e + "\n");
	        }
	    }

	    System.out.println("Fine sincronizzazione");
	}

}
