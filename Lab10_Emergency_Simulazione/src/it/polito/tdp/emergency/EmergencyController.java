package it.polito.tdp.emergency;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.emergency.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmergencyController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtDott;

    @FXML
    private TextField txtTempo;
    
    @FXML
    private Button btnInserisci;

    @FXML
    private Button btnSimulazione;

    @FXML
    private TextArea txtResult;

    public void setModel(Model model){
    	this.model=model;
    	model.aggiungiPazienti();
    	model.aggiungiEventi();
    }
    
    @FXML
    void doInserici(ActionEvent event) {
    	
    	if(txtDott.getText().compareTo("")==0){
    		txtResult.setText("ERRORE inserire dottore!");
    	}else
    		model.aggiungiMedico(txtDott.getText(),Long.parseLong(txtTempo.getText()));
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	String s=model.simula().toString();
    	txtResult.setText(s);
    }

    @FXML
    void initialize() {
        assert txtDott != null : "fx:id=\"txtDott\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnInserisci != null : "fx:id=\"btnInserisci\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Emergency.fxml'.";

    }
}
