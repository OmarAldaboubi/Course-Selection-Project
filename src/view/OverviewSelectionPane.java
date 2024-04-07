package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import model.Module;
import model.StudentProfile;


public class OverviewSelectionPane extends GridPane {
	
    private TextArea profileTextArea, modulesTextArea,reservedModulesTextArea;
    private Button saveOverview;
    
    public OverviewSelectionPane() {
    this.setVgap(10);
	this.setHgap(10);
	this.setAlignment(Pos.CENTER);
	
	 profileTextArea = new TextArea();
    profileTextArea.setEditable(false);

     modulesTextArea = new TextArea();
    modulesTextArea.setEditable(false);

     reservedModulesTextArea = new TextArea();
    reservedModulesTextArea.setEditable(false);
    saveOverview = new Button("Save Overview");
    
    this.add(new Label("Profile Information:"), 0, 0);
    this.add(profileTextArea, 0, 1, 2, 1);
    this.add(new Label("Selected Modules:"), 0, 2);
    this.add(modulesTextArea, 0, 3, 2, 1);
    this.add(new Label("Reserved Modules:"), 0, 4);
    this.add(reservedModulesTextArea, 0, 5, 2, 1);
    this.add(saveOverview, 0, 6, 2,1);
    }
    
    public void setProfileInformation(StudentProfile studentProfile) {
        profileTextArea.setText("Profile Information:\n" +
                "Pnumber: " + studentProfile.getStudentPnumber() + "\n" +
                "Name: " + studentProfile.getStudentName().getFullName() + "\n" +
                "Email: " + studentProfile.getStudentEmail() + "\n" +
                "Submission Date: " + studentProfile.getSubmissionDate() + "\n" +
                "Course: " + studentProfile.getStudentCourse());
    }

    public void setSelectedModules(String selected) {
        
        modulesTextArea.setText("Selected Modules:\n" + selected);
    }
        public void setReservedModules(String reservedModules) {
            
            reservedModulesTextArea.setText("Reserved Modules:\n" + reservedModules);
        }
        public void addSaveoverviewHandlerOSP(EventHandler <ActionEvent> handler) {
			saveOverview.setOnAction(handler);
        }
}
   
	  
	        
	   
        
        
        
    

