package tesi;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import io.github.cdimascio.dotenv.Dotenv;


public class Applicazione {
    public static void main(String[] args) {
    	
        Dotenv dotenv = Dotenv.configure()
                             .directory(System.getProperty("user.dir")) // root del progetto
                             .load();

        String clientId = dotenv.get("AZURE_CLIENT_ID");
        String tenantId = dotenv.get("AZURE_TENANT_ID");
        String clientSecret = dotenv.get("AZURE_CLIENT_SECRET");
        String subscriptionId = dotenv.get("AZURE_SUBSCRIPTION_ID");
        String resourceGroupName = dotenv.get("AZURE_RESOURCE_GROU");

        // Controllo rapido per evitare problemi
        if (clientId == null || tenantId == null || clientSecret == null || subscriptionId == null) {
            System.err.println("Mancano variabili nel .env. Controlla AZURE_CLIENT_ID, AZURE_TENANT_ID, AZURE_CLIENT_SECRET, AZURE_SUBSCRIPTION_ID");
            System.exit(1);
        }

        System.out.println("Tentativo di autenticazione ad Azure usando ClientSecretCredential...");

        try {
            TokenCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .tenantId(tenantId)
                    .clientSecret(clientSecret)
                    .build();

            AzureProfile profile = new AzureProfile(tenantId, subscriptionId, AzureEnvironment.AZURE);

            AzureResourceManager azure = AzureResourceManager
                    .authenticate(credential, profile)
                    .withSubscription(subscriptionId);

            System.out.println("Autenticazione riuscita! Elenco VM in: " + resourceGroupName);
            for (VirtualMachine vm : azure.virtualMachines().listByResourceGroup(resourceGroupName)) {
                System.out.println("Nome VM: " + vm.name());
                System.out.println("OS: " + vm.osType());
                System.out.println("Stato: " + vm.powerState());
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.err.println("Errore durante la connessione ad Azure:");
            e.printStackTrace();
        }
    }
}
