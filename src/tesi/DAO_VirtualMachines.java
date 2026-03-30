package tesi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.azure.resourcemanager.compute.models.VirtualMachine;

public class DAO_VirtualMachines {
	
	
	
	public void aggiungiVM(VirtualMachine vm) throws SQLException {
		
	    System.out.println("Inizio operazione di INSERT...");

		Connection conn = DatabaseConnector.creaConnessione();
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
	
	public LinkedList<VMRecord> getAllVM() throws SQLException {
		
		System.out.println("Inizio operazione di SELECT...");

		Connection conn = DatabaseConnector.creaConnessione();
		Statement st = conn.createStatement();
		
		String query = "SELECT * FROM virtual_machines";
		ResultSet rs = st.executeQuery(query);
		
		LinkedList<VMRecord> risultato = new LinkedList<VMRecord>();
		while ( rs.next() ) {
			String id = rs.getString("ID");
			String nome = rs.getString("nome");
			String os = rs.getString("sistema_operativo");
			String stato = rs.getString("stato");
			
			VMRecord vm = new VMRecord(id, nome, os, stato);
			risultato.add(vm);
		}
		
		st.close();
		conn.close();
	    System.out.print("Operazione di SELECT conclusa, connessione chiusa");
	    return risultato;
	}
	
	
	
	
	
}
