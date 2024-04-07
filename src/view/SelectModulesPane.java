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
import model.Course;
import model.Module;

import java.util.Collection;
import java.util.List;

import controller.FinalYearOptionsController;
import view.CreateStudentProfilePane;

public class SelectModulesPane extends GridPane {
	private ListView<Module> block1Modules;
	private ListView<Module> block2Modules;
	private ListView<Module> block34Modules;
	private ListView<Module> unblock34Modules; 
	private Button addButton, removeButton, resetButton ,submitButton;
	private Label credits;
	
	public  SelectModulesPane() {
		this.setVgap(10);
		this.setHgap(10);
		this.setAlignment(Pos.CENTER);

		block1Modules = new ListView<Module>();
		block2Modules = new ListView<Module>();
        unblock34Modules = new ListView<Module>();
        block34Modules = new ListView<Module>();
        
        Label txtBlockOne = new Label ("Selected Block 1 Modules");
        Label txtBlockTwo = new Label ("Selected Block 2 Modules");
        Label txtBlockThreeFour = new Label ("Selected Block 3/4 Modules");
        Label txtUnBlockThreeFour = new Label ("Unselected Block 3/4 Modules");
         credits = new Label("Accumulated Credits: 0");

         addButton = new Button("Add");
        removeButton = new Button("Remove");
         resetButton = new Button("Reset");
         submitButton = new Button("Submit");

        
        

        this.add(txtBlockOne, 0, 0);
        this.add(block1Modules, 0, 1);
        this.add(txtBlockTwo, 1, 0);
        this.add(block2Modules, 1, 1 );
        this.add(txtBlockThreeFour, 0, 2 );
        this.add(block34Modules, 0 ,3 );
        this.add(txtUnBlockThreeFour, 1, 2);
        this.add(unblock34Modules, 1, 3);
        this.add(credits, 1, 4);
        this.add(addButton, 0, 5);
        this.add(removeButton, 0, 6);
        this.add(resetButton, 1, 5);
        this.add(submitButton, 1, 6);
        
    
        
    }
	
	public void updateSelectModulesPane(Collection<Module> selectedCourse) {	
	   
	   
	    for (Module module : selectedCourse) {
	        if (module.isMandatory()) {
	            switch (module.getRunPlan()) {
	                case BLOCK_1:
	                      block1Modules.getItems().addAll(module);
	                    break;
	                case BLOCK_2:
	                     block2Modules.getItems().addAll(module);
	                    break;
	                case BLOCK_3_4:
	                      block34Modules.getItems().addAll(module);
	                    break;
	            }
	        }
	    }
	    for (Module module : selectedCourse) {
	        if (!module.isMandatory()) {
	              unblock34Modules.getItems().addAll(module);
	        }
	        
	    }
	}
	public ListView<Module> getBlock1ListView(){
		return block1Modules;
	}
	public ListView<Module> getBlock2ListView(){
		return block2Modules;
	}

	public ListView<Module> getUnselectedListView() {
		return   unblock34Modules;
	}
	 
	public ListView<Module> getSelectedListView(){
		return block34Modules;
	}
	public Collection<Module> getBlock1Collection(){
		Collection<Module> selected1234 = block1Modules.getItems();
		return selected1234;
	}
	public Collection<Module> getBlock2Collection(){
		Collection<Module> selected1234 = block2Modules.getItems();
		return selected1234;
	}
	public Collection<Module> getselectedBlock34Collection(){
		Collection<Module> selected1234 = block34Modules.getItems();
		return selected1234;
		
	}
	public Collection<Module> getUnselectedBlock34Collection(){
		Collection<Module> selected123 = unblock34Modules.getItems();
			return selected123;
			
	}
	
	public int calculateAccumulatedCredits() {
		int accumulatedCredits = 0;
		for (Module module : block1Modules.getItems()) {
            accumulatedCredits += module.getModuleCredits();
		}
		for (Module module : block2Modules.getItems()) {
            accumulatedCredits += module.getModuleCredits();
		}

        for (Module module : block34Modules.getItems()) {
            accumulatedCredits += module.getModuleCredits();
        }

        return accumulatedCredits;
    }

	public void updateCredits() {
		 int accumulatedCredits = calculateAccumulatedCredits();
	        credits.setText("Accumulated Credits: " + accumulatedCredits);
	        
		
        
	}
	
	
	public void addAddHandler(EventHandler <ActionEvent> handler) {
	 addButton.setOnAction(handler);
	}
	public void addremoveHandler(EventHandler <ActionEvent> handler) {
		removeButton.setOnAction(handler);
		
	}
	public void addresetHandler(EventHandler <ActionEvent> handler) {
		resetButton.setOnAction(handler);
}
	public void addsubmitHandler(EventHandler <ActionEvent> handler) {
		submitButton.setOnAction(handler);
}
}


