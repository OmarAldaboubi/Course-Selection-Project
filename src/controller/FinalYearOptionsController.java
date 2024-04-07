package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Block;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;
import view.ReserveModulePane;
import view.OverviewSelectionPane;
public class FinalYearOptionsController {

	//fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private SelectModulesPane smp;
	private ReserveModulePane rmp;
	private OverviewSelectionPane osp;
	public FinalYearOptionsController(FinalYearOptionsRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulePane();
		osp = view.getOverviewSelectionPane();
		
		//add courses to combobox in create student profile pane using the buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		mstmb.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null, "This Application is for Students to select their Modules for their blocks depending on their course"));
		smp.addAddHandler(new AddHandlerSMP());
		smp.addremoveHandler(new removeHandlerSMP());
		smp.addresetHandler(new resetHandlerSMP());
		smp.addsubmitHandler(new submitHandlerSMP());
		rmp.addAddHandlerRMP(new addHandlerRMP());
		rmp.addremoveHandlerRMP(new removeHandlerRMP());
		rmp.addConfirmHandlerRMP(new ConfirmHandlerRMP());
		osp.addSaveoverviewHandlerOSP(new saveOverviewHandlerOSP());
		mstmb.addSaveHandler(new SaveProfileHandler());
		mstmb.addLoadHandler(new LoadMenuHandler());
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
	}
	
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Name student = cspp.getStudentName();
			if(student.getFirstName().equals("")|| student.getFamilyName().equals("")) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Need to input both first and surname!");
			}else {
			model.setStudentCourse(view.getCreateStudentProfilePane().getSelectedCourse());
			model.setStudentPnumber(view.getCreateStudentProfilePane().getStudentPnumber());
			model.setStudentName(view.getCreateStudentProfilePane().getStudentName());
			model.setStudentEmail(view.getCreateStudentProfilePane().getStudentEmail());
			model.setSubmissionDate(view.getCreateStudentProfilePane().getStudentDate());
			smp.updateSelectModulesPane(model.getStudentCourse().getAllModulesOnCourse());
			smp.updateCredits();
			view.changeTab(1);
		}
	}
	}
	private class AddHandlerSMP implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) { 
			 int currentCredits = smp.calculateAccumulatedCredits();
			 
			Module selectedModules = smp.getUnselectedListView().getSelectionModel().getSelectedItem();
			if (currentCredits >= 120) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Cant exceed 120 credits.");
			
			}else {
				smp.getUnselectedListView().getItems().remove(selectedModules);
	            smp.getSelectedListView().getItems().add(selectedModules);
	            smp.calculateAccumulatedCredits();
	            smp.updateCredits();
				
			}
            
            
		}
	}
		
	private class removeHandlerSMP implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
		
			Module selectedModules = smp.getSelectedListView().getSelectionModel().getSelectedItem();
			if (selectedModules != null) {
				if (!selectedModules.isMandatory()) {
				smp.getUnselectedListView().getItems().add(selectedModules);
            	smp.getSelectedListView().getItems().remove(selectedModules);
            	smp.updateCredits();
				}else {
					alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Action not permitted.");
				}
		}
		}
	}
	
	private class resetHandlerSMP implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			smp.getBlock1ListView().getItems().clear();
			smp.getBlock2ListView().getItems().clear();
			smp.getUnselectedListView().getItems().clear();
			smp.getSelectedListView().getItems().clear();
			smp.updateSelectModulesPane(model.getStudentCourse().getAllModulesOnCourse());
			smp.updateCredits();
		
		}
	}
		private class submitHandlerSMP implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				int currentCredits = smp.calculateAccumulatedCredits();
				if (currentCredits != 120) {
					alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "Please select a module from the Unselected Block3/4 Modules.");
				}else {
			        
				
				for (Module selected : smp.getBlock1Collection()) {
					model.addSelectedModule(selected);
            }
				for (Module selected : smp.getBlock2Collection()) {
                		model.addSelectedModule(selected);
            }
				for (Module selected : smp.getselectedBlock34Collection()) {
						model.addSelectedModule(selected);
            }
				rmp.addUnblock34(smp.getUnselectedBlock34Collection());
            view.changeTab(2);
            alertDialogBuilder(AlertType.INFORMATION, "ATTENTION", null, "Please select only 1 module to reserve.");
            }
        }
		}	
		
    
			
		private class addHandlerRMP implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				
				Module selectedModules = rmp.getUnselectedBlock34().getSelectionModel().getSelectedItem();
				if (selectedModules != null) {
				rmp.getUnselectedBlock34().getItems().remove(selectedModules);
	            rmp.getReservedBlock34().getItems().add(selectedModules);
			}
		}
		}
		private class removeHandlerRMP implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				
				Module selectedModules = rmp.getReservedBlock34().getSelectionModel().getSelectedItem();
				if (selectedModules != null) {
				rmp.getUnselectedBlock34().getItems().add(selectedModules);
	            rmp.getReservedBlock34().getItems().remove(selectedModules);
			}
			}
		}
		private class ConfirmHandlerRMP implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				for(Module reservedModules: rmp.getReservedCollection()) {
				if (reservedModules != null) {
					model.addReservedModule(reservedModules);
				}
					
					StudentProfile studentProfile = model;  
			        osp.setProfileInformation(studentProfile);
			        
			        String selected = "";
			        for (Module module: model.getAllSelectedModules()){
			        	selected = selected +"\n ModuleCode:  "+ module.getModuleCode()+ " \n Module Name:  " + module.getModuleName()+ "\n ModuleCredits:  " + module.getModuleCredits()+ "\n Is Module Mandatory:  "+ module.isMandatory()+"\n ModuleBlock:  " + module.getRunPlan();
			        }
			        osp.setSelectedModules(selected);

			        String reserved = "";
			        for (Module module: model.getAllReservedModules()){
			        	reserved = reserved +"\n ModuleCode:  "+ module.getModuleCode()+ " \n Module Name:  " + module.getModuleName()+ "\n ModuleCredits:  " + module.getModuleCredits()+ "\n Is Module Mandatory:  "+ module.isMandatory()+"\n ModuleBlock:  " + module.getRunPlan();
			        osp.setReservedModules(reserved);
			        
			        view.changeTab(3);
				}
				}
			}
		
		}
		private class saveOverviewHandlerOSP implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				try (PrintWriter save = new PrintWriter(new FileWriter("studentOverview.txt"))) {
					save.println("Student Details:");
					save.println("PNumber: " + model.getStudentPnumber());
					save.println("Name: " + model.getStudentName().getFullName());
					save.println("Email: " + model.getStudentEmail());
					save.println("Date of Submission: " + model.getSubmissionDate());
					save.println("Course: " + model.getStudentCourse().getCourseName());
					save.println();		            
					save.println("Selected Modules:");
		            model.getAllSelectedModules().forEach(module -> save.println(module.getModuleCode() + " - " + module.getModuleName()));
		            save.println();		            
		            save.println("Reserved Modules:");
		            model.getAllReservedModules().forEach(module -> save.println(module.getModuleCode() + " - " + module.getModuleName()));

		            alertDialogBuilder(AlertType.INFORMATION, "Save Success", "Student Overview Saved", "Student overview saved to studentOverview.txt");
		        } catch (IOException exception) {
		            exception.printStackTrace();
		            alertDialogBuilder(AlertType.ERROR, "Save Error", "Error Saving Student Overview", "An error occurred while saving the student overview.");
		        }
		    }
			}
		private class SaveProfileHandler implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
	       
	        }
		}

	
	        
	    
		
		private class LoadMenuHandler implements EventHandler<ActionEvent> {

			public void handle(ActionEvent e) {
				
			}	
		}
		

	//helper method - builds modules and course data and returns courses within an array
	private Course[] buildModulesAndCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);


		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
	
	
    
    
private void alertDialogBuilder(AlertType type, String title, String header, String content) {
	Alert alert = new Alert(type);
	alert.setTitle(title);
	alert.setHeaderText(header);
	alert.setContentText(content);
	alert.showAndWait();
}
}

