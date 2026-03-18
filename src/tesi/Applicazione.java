package tesi;

import java.sql.*;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import io.github.cdimascio.dotenv.Dotenv;


public class Applicazione {
    public static void main(String[] args) {

    	// in questo modo accedo al file .env su cui poi posso usare dei metodi getter per ottenere le credenziali
        Dotenv dotenv = Dotenv.configure()
                             .directory(System.getProperty("user.dir")) // root del progetto
                             .load();

        String clientId = dotenv.get("AZURE_CLIENT_ID");
        String tenantId = dotenv.get("AZURE_TENANT_ID");
        String clientSecret = dotenv.get("AZURE_CLIENT_SECRET");
        String subscriptionId = dotenv.get("AZURE_SUBSCRIPTION_ID");
        String resourceGroupName = dotenv.get("AZURE_RESOURCE_GROUP");
        
        String passwordDatabase = dotenv.get("DATABASE_PASSWORD");
        

        // Controllo rapido per evitare problemi con le credenziali
        if (clientId == null || tenantId == null || clientSecret == null || subscriptionId == null) {
            System.err.println("Mancano variabili nel .env"); // fa una stampa rossa per evidenziare che ci sia un'errore
            System.exit(1); // termina il codice per evitare errori successivi
        }

        System.out.println("Tentativo di autenticazione ad Azure usando ClientSecretCredential...");

        try {
        	// 1) creo una entità che rappresenta tutte le credenziali di accesso
            TokenCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .tenantId(tenantId)
                    .clientSecret(clientSecret)
                    .build();

            // 2) definisco un'oggetto che rappresenta il mio profilo Azure
            AzureProfile profile = new AzureProfile(tenantId, subscriptionId, AzureEnvironment.AZURE);

            // 3) con le credenziali creo il manager, ossia l'oggetto che ha potere di azione sulle VM nel profilo
            AzureResourceManager azureResourceManager = AzureResourceManager
                    .authenticate(credential, profile)
                    .withSubscription(subscriptionId);
            
            
            
            // ***************** mi connetto al database ********************
            try {
            	// sto prendento un database di tipo mysql, salvato in un servere locale (il mio calcolatore), nominato db_tesi e con la mia password"
    			String url = "jdbc:mysql://localhost/db_tesi?user=root&password="+passwordDatabase;		

    			Connection conn = DriverManager.getConnection(url); // questo oggetto rappresenta la connessione con il database
    			
    			PreparedStatement ps = conn.prepareStatement("INSERT INTO virtual_machines(ID, nome, sistema_operativo, stato) VALUES(?, ?, ?, ?)");
    			
    			// con il resourceManager ottengo la lista delle VM disponibili, così posso iterare sui suoi elementi e ripetere l'INSERT autonomamente
    			for (VirtualMachine vm : azureResourceManager.virtualMachines().listByResourceGroup(resourceGroupName)) {
	    	 
	    	        System.out.println("salvaggio VM: " + vm.name()); // tengo una traccia della VM su cui sto lavorando
	    			
	    		    ps.setString(1, vm.vmId());
	    		    ps.setString(2, vm.name());
	    		    ps.setString(3, vm.osType().toString());
	    		    ps.setString(4, vm.powerState().toString());
	    		    
	    		    ps.executeUpdate(); // The insert is executed here
	    		    ps.clearParameters(); // questo metodo serve a eliminare i parametri che della VM che è gia stata registrata per far spazio ai prossimi,
	    		    					  // non e fonfamentale, ma è per assicurare che non ci siano errori
    			}
    			ps.close();
    			conn.close();
    		}
    		catch (Exception e) {
                System.err.println("Errore durante la connessione al Database:");
    			e.printStackTrace();
    		} 
            //******************fine connessione con il database***************
        
        }
        catch (Exception e) {
            System.err.println("Errore durante la connessione ad Azure:");
            e.printStackTrace();
        }
    }
}
