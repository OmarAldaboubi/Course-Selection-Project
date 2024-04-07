package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import view.SelectModulesPane;
import model.Module;

import java.util.Collection;


import controller.FinalYearOptionsController;

public class ReserveModulePane extends GridPane {
	private ListView<Module> unSelectedBlock34Modules;
	private ListView<Module> reservedBlock34Modules;
	private Button addButton,removeButton,confirmButton;
	public ReserveModulePane() {
		this.setVgap(15);
		this.setHgap(15);
		this.setAlignment(Pos.CENTER);
		
		unSelectedBlock34Modules = new ListView<Module>();
		reservedBlock34Modules = new ListView<Module>();
		
		Label unselectedBlock34 = new Label ("Unselected Block 3/4 modules");
		Label reservedBlock34 = new Label ("Reserved Block 3/4 modules");
		
		 addButton = new Button("Add");
		 removeButton = new Button("Remove");
		 confirmButton = new Button("Confirm");
		
		this.add(unselectedBlock34, 0, 0);
		this.add(unSelectedBlock34Modules,0, 1);
		this.add(reservedBlock34, 1, 0);
		this.add(reservedBlock34Modules, 1, 1);
		this.add(addButton, 0, 2);
	    this.add(removeButton, 1, 2);
	    this.add(confirmButton, 1, 3);
	}
	    public void addUnblock34(Collection<Module> modules) {
	    	for(Module chosen: modules) {
	    		unSelectedBlock34Modules.getItems().addAll(chosen);
	    	}
	    }
	    public Collection<Module> getReservedCollection(){
			Collection<Module> reserved = reservedBlock34Modules.getItems();
			return reserved;
	    }
	    	
	    public ListView<Module> getUnselectedBlock34(){
	    	return unSelectedBlock34Modules;
	    }
	    public ListView<Module> getReservedBlock34(){
	    	return reservedBlock34Modules;
	    }
	    public void addAddHandlerRMP(EventHandler <ActionEvent> handler) {
	   	 addButton.setOnAction(handler);
	   	}
	    public void addremoveHandlerRMP(EventHandler <ActionEvent> handler) {
			removeButton.setOnAction(handler);
			
			
		}
	    public void addConfirmHandlerRMP(EventHandler <ActionEvent> handler) {
			confirmButton.setOnAction(handler);
}
}

