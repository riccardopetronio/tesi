package OLD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import com.azure.resourcemanager.compute.models.VirtualMachine;

import tesi.vm.VMRecord;

public class OLD_DAO_VirtualMachines {
	
	
	
	public static void aggiungiVM(VirtualMachine vm) throws SQLException {
		
	    System.out.println("Inizio operazione di INSERT...");

		Connection conn = OLD_DatabaseConnector.creaConnessione();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO virtual_machines(ID, nome, sistema_operativo, stato) VALUES(?, ?, ?, ?)");
		
		ps.setString(1, vm.vmId());
	    ps.setString(2, vm.name());
	    ps.setString(3, vm.osType().toString());
	    ps.setString(4, vm.powerState().toString());
	    
	    ps.executeUpdate();   // qui è dove le modifiche alla tabella sono effettivamente eseguite
	    ps.clearParameters(); // questo metodo serve a eliminare i parametri che della VM che è gia stata registrata per far spazio ai
		  					  // prossimi, non e fonfamentale, ma è per assicurare che non ci siano errori
	    ps.close();
	    conn.close();
	    
	    System.out.println("Operazione riuscira, chiudo la connessione\n");
	}
	
	public static TreeMap<String, VMRecord> getAllVM() throws SQLException {
		
		System.out.println("Inizio operazione di SELECT...");

		Connection conn = OLD_DatabaseConnector.creaConnessione();
		Statement st = conn.createStatement();
		
		String query = "SELECT * FROM virtual_machines";
		ResultSet rs = st.executeQuery(query);
		
		TreeMap<String, VMRecord> risultato = new TreeMap<String, VMRecord>();
		while ( rs.next() ) {
			String id = rs.getString("ID");
			String nome = rs.getString("nome");
			String os = rs.getString("sistema_operativo");
			String stato = rs.getString("stato");
			
			VMRecord vm = new VMRecord(id, nome, os, stato);
			risultato.put(id, vm);
		}
		
		st.close();
		conn.close();
	    System.out.print("Operazione di SELECT conclusa, connessione chiusa\n");
	    return risultato;
	}
	
	public static void aggiornaVM(VMRecord vm) throws SQLException {
	    String sql = "UPDATE virtual_machines SET nome = ?, sistema_operativo = ?, stato = ? WHERE ID = ?";

	    try (Connection conn = OLD_DatabaseConnector.creaConnessione();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, vm.getNome());
	        ps.setString(2, vm.getOs());
	        ps.setString(3, vm.getStato());
	        ps.setString(4, vm.getId());

	        ps.executeUpdate();
	    }
	}

	public static void eliminaVM(String id) throws SQLException {
	    String sql = "DELETE FROM virtual_machines WHERE ID = ?";

	    try (Connection conn = OLD_DatabaseConnector.creaConnessione();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, id);
	        ps.executeUpdate();
	    }
	}
}
