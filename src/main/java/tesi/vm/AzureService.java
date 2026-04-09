package tesi.vm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;

@Service
public class AzureService {

	@Autowired
    private AzureResourceManager manager;  // spring si occupa di creare e gestire il manager, non devo rifarlo per ogni metodo
	

	public void spegliVirtualMachineSuAzure(String resurceGroup, String nomeVM) {
		manager.virtualMachines().deallocate(resurceGroup, nomeVM);
	}
	public void accendiVirtualMachineSuAzure(String resurceGroup, String nomeVM) {
		manager.virtualMachines().start(resurceGroup, nomeVM);
	}
	public PagedIterable<VirtualMachine> getListAzureVM(String resourceGroupName) {
		return manager.virtualMachines().listByResourceGroup(resourceGroupName);
	}
	
}
