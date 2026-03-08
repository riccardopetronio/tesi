package tesi;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;

public class Applicazione {

	public static void main(String[] args) {

		// 1. Definiamo le credenziali che ti ha fornito il referente
        String clientId = "f246a0ce-1686-4165-ae83-a1a1e2a43ec8";
        String tenantId = "54cfecbb-c772-47b5-bf41-36f675af3083";
        String clientSecret = "QMY8Q~b~ePrH5jWfkHgDHrS2CcZozAcgpUwHmaXV"; // Incolla qui il segreto
        String subscriptionId = "dbe224e1-d261-4619-a880-b9f5b8c7b1d9";
        String resourceGroupName = "tesi-riccardo";
		
        
        System.out.println("Tentativo di autenticazione ad Azure in corso...");

        try {
            // 2. Creiamo l'oggetto per l'autenticazione
            TokenCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .tenantId(tenantId)
                    .clientSecret(clientSecret)
                    .build();

            AzureProfile profile = new AzureProfile(tenantId, subscriptionId, AzureEnvironment.AZURE);

            // 3. Autentichiamo il Resource Manager
            AzureResourceManager azure = AzureResourceManager
                    .authenticate(credential, profile)
                    .withSubscription(subscriptionId);

            System.out.println("Autenticazione riuscita! Ricerca macchine virtuali...");

            // 4. Cerchiamo le VM nel tuo Resource Group
            System.out.println("--- Elenco VM nel Resource Group: " + resourceGroupName + " ---");
            
            for (VirtualMachine vm : azure.virtualMachines().listByResourceGroup(resourceGroupName)) {
                System.out.println("Nome VM: " + vm.name());
                System.out.println("Sistema Operativo: " + vm.osType());
                System.out.println("Stato attuale: " + vm.powerState());
                System.out.println("-----------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la connessione ad Azure:");
            e.printStackTrace();
        }
	}

}
