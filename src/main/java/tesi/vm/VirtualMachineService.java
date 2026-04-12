package tesi.vm;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.azure.resourcemanager.compute.models.VirtualMachine;

@Service 
public class VirtualMachineService {
	
	@Autowired // Spring "inserisce" qui il repository automaticamente
	private VMRepository VMRepository;
	@Autowired
	@Lazy
	private AzureService as;
	
	public void salvaVirMacDaAzureAlDB(String resourceGroupName) {

	    System.out.println("Inizio sincronizazione a Azure...");

	    for (VirtualMachine vmAzure : this.as.getListAzureVM(resourceGroupName)) {

	        System.out.println("Salvataggio VM: " + vmAzure.name());
	        VMRecord vTemp = new VMRecord(vmAzure.vmId(), vmAzure.computerName(), vmAzure.osType().toString(), vmAzure.powerState().toString());
	        
	        this.VMRepository.save(vTemp);
	    }  
	    System.out.println("Fine sincronizzazione");
	}
	
	public List<VMRecord> getAllVMDalDB(){
		return this.VMRepository.findAll();
	}
	
	public void salvaVirtualMachineSulDB(VMRecord vm) {
		this.VMRepository.save(vm);
	}
	
	public void sincronizzaVM(String resourceGroupName) {

        // lista per tenere traccia delle VM viste su Azure, serve dopo per capire quali eliminare dal DB
        LinkedList<String> idVisti = new LinkedList<String>();

        // Ciclo su tutte le VM presenti nel resource group su Azure
        for (VirtualMachine vmAzure : this.as.getListAzureVM(resourceGroupName)) {

            String id = vmAzure.vmId();
            idVisti.add(id); // segno che questa VM esiste su Azure

	        VMRecord vTemp = new VMRecord(vmAzure.vmId(), vmAzure.computerName(), vmAzure.osType().toString(), vmAzure.powerState().toString());
            this.VMRepository.save(vTemp);
        }

        // Controllo se nel DB ci sono VM che NON esistono più su Azure
        for (VMRecord record : this.getAllVMDalDB()) {
            if (!idVisti.contains(record.getId())) {
                // Esiste nel DB ma non su Azure → la elimino
                this.VMRepository.delete(record);
            }
        }
    }
}
