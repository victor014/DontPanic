package sample.Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Controllers.Configs.*;

public class Controller extends Application {

    private Stage stage;

    @FXML
     HBox panesote;
    @FXML
     TextArea txtConsola;
    @FXML
    TreeView TreeProyecto;

    CodeArea codeArea=new CodeArea();

    @FXML protected  void initialize(){
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(0, 0, sampleCode);
        codeArea.setPrefSize(360,300);
        Subscription cleanupWhenNoLongerNeedIt = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        HBox.setHgrow(codeArea, Priority.ALWAYS);
        panesote.getChildren().add(codeArea);

        //https://github.com/grijalvaromero/CompiladorRecetas
        //AGREGAR CLASE CSS AL EDITOR

        //PRUEBA DE TREEVIEW
        TreeItem<String> Nombre=new TreeItem<>("Proyecto Demo");
        TreeItem<String> prueba2=new TreeItem<>("src");
        TreeItem<String> prueba3=new TreeItem<>("Controladores");
        TreeProyecto.setRoot(Nombre);
        Nombre.getChildren().add(prueba2);
        Nombre.getChildren().add(prueba3);



    }



    public void evtSalir(ActionEvent evento){System.exit(0);}

    public void evtAbrir(ActionEvent evt){
        FileChooser of=new FileChooser();
        of.setTitle("Abrir archivo DontPanic");
        FileChooser.ExtensionFilter filtro=new FileChooser.ExtensionFilter("Archivos .dpc","*.dpc");
        of.getExtensionFilters().add(filtro);
        File file=of.showOpenDialog(stage);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
    }

    public void ejecutar(ActionEvent event){
        compilar();
    }

    public void compilar(){
        txtConsola.setText("");
        long tInicial=System.currentTimeMillis();
        String texto=codeArea.getText();
        String[] renglones=texto.split("\\n");

        for (int x=0;x<renglones.length;x++) {
            boolean bandera = false;
            if (!renglones[x].trim().equals("")) {
                for (int y = 0; y < Configs.EXPRESIONES.length && bandera == false; y++) {
                    Pattern patron = Pattern.compile(Configs.EXPRESIONES[y]);
                    Matcher matcher = patron.matcher(renglones[x]);
                    if (matcher.matches()) {
                        //txtConsola.setText(txtConsola.getText() + "\nERROR DE SINTAXIS EN LA LINEA " + (x+1));
                        bandera = true;
                    }
                }
                if (bandera == false) {
                    txtConsola.setText(txtConsola.getText() + "\nERROR DE SINTAXIS EN LA LINEA " + (x + 1));
                }
            }
        }
        long Tfinal=System.currentTimeMillis()-tInicial;
        txtConsola.setText(txtConsola.getText()+"\n\nCOMPILADO EN " + Tfinal + " MILISEGUNDOS");
    }

}