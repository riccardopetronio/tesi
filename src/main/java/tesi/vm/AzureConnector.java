package tesi.vm;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import io.github.cdimascio.dotenv.Dotenv;

@Component
public class AzureConnector {
	
	@Bean
	public AzureResourceManager getManager() {
		
		
	    System.out.println("Inizio la connessione a azure...");

		// in questo modo accedo al file .env su cui poi posso usare dei metodi getter per ottenere le credenziali
	    Dotenv dotenv = Dotenv.configure()
	                         .directory(System.getProperty("user.dir")) // root del progetto
	                         .load();
	
	    String clientId = dotenv.get("AZURE_CLIENT_ID");
	    String tenantId = dotenv.get("AZURE_TENANT_ID");
	    String clientSecret = dotenv.get("AZURE_CLIENT_SECRET");
	    String subscriptionId = dotenv.get("AZURE_SUBSCRIPTION_ID");
	    
	    // Controllo rapido per evitare problemi con le credenziali
	    if (clientId == null || tenantId == null || clientSecret == null || subscriptionId == null) {
	        System.err.println("Mancano variabili nel .env"); // fa una stampa rossa per evidenziare che ci sia un'errore
		    throw new RuntimeException("Errore mancano delle credenziali di accesso"); // termina il codice per evitare errori successivi
	    }
	    
	    
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
	        
		    System.out.println("Aurentificazione ad azure riuscita, ritorno il azureResourceManager\n");
	        return azureResourceManager;
		}
	    
	    catch (Exception e) {
		    throw new RuntimeException("Errore durante la connessione a Azure", e);
		}
	    
	}    
}


