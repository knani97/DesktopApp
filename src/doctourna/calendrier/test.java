/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.calendrier;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import doctourna.console.Console;
import doctourna.controllers.ConsultTacheController;
import doctourna.controllers.ModifTacheController;
import doctourna.models.Tache;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

public class test extends Application {

    public static ServiceCalendrier sc = new ServiceCalendrier();
    public static ServiceTache st = new ServiceTache();
    public static Calendar rdvs = new Calendar("RDVs");
    public static Calendar meds = new Calendar("Prises Médicaments");
    public static Calendar persos = new Calendar("Personnelles");
    public static Calendar dispos = new Calendar("Disponibilités");
    public static Calendar rdvsPersos = new Calendar("RDVs Personnelles");
    public static CalendarView calendarView = new CalendarView();
    public static InfoCalController infoCal = new InfoCalController();

    public static void reset() {
        for (Tache t : st.findByCalendrier(sc.findByUid(Session.getId()).getId())) {
            Entry<Tache> event = new Entry<>(t.getLibelle());

            event.setId(t.getId().toString());

            event.changeStartDate(t.getDate().toLocalDateTime().toLocalDate(), false);
            event.changeStartTime(t.getDate().toLocalDateTime().toLocalTime(), false);

            LocalDateTime dateTimeEnd = t.getDate().toLocalDateTime();
            dateTimeEnd = dateTimeEnd.plusHours(t.getDuree().toLocalTime().getHour());
            dateTimeEnd = dateTimeEnd.plusMinutes(t.getDuree().toLocalTime().getMinute());
            event.changeEndDate(dateTimeEnd.toLocalDate(), false);
            event.changeEndTime(dateTimeEnd.toLocalTime(), false);

            event.setUserObject(t);

            switch (t.getType()) {
                case "1":
                    rdvs.addEntry(event);
                    break;
                case "2":
                    meds.addEntry(event);
                    break;
                case "3":
                    persos.addEntry(event);
                    break;
                case "4":
                    dispos.addEntry(event);
                    break;
                case "5":
                    rdvsPersos.addEntry(event);
                    break;
                default:
                    System.out.println("Type non-reconnu.");
                    break;
            }
        }
    }

    public static void refresh() {
        rdvs.clear();
        meds.clear();
        persos.clear();
        dispos.clear();
        rdvsPersos.clear();
        reset();
    }
    
    public static void resetStats() {
        test.infoCal.reset();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        rdvs.setStyle(Style.STYLE5);
        meds.setStyle(Style.STYLE2);
        persos.setStyle(Style.STYLE4);
        dispos.setStyle(Style.STYLE1);
        rdvsPersos.setStyle(Style.STYLE3);

        rdvs.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("Types");
        myCalendarSource.getCalendars().addAll(rdvs, meds, persos, dispos, rdvsPersos);

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendrier: Mise à Jour Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        ;
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        reset();
        
        FXMLLoader loaderN2 = new FXMLLoader(
                getClass().getResource(
                        "infocal.fxml"
                )
        );
        Node n2 = loaderN2.load();
        calendarView.setFooter(n2);
        
        FXMLLoader loaderN = new FXMLLoader(
                getClass().getResource(
                        "calendarheader.fxml"
                )
        );
        Node n = loaderN.load();
        ((CalendarHeaderController) loaderN.getController()).primaryStage = primaryStage;
        calendarView.setHeader(n);
        
        test.infoCal = loaderN2.getController();

        PopOver popOver = new PopOver();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../ui/consulttache.fxml"
                )
        );
        AnchorPane p = loader.load();
        ConsultTacheController controller = loader.getController();
        popOver.setContentNode(p);
        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            controller.setTache(Integer.parseInt(param.getEntry().getId()));
            return popOver.getContentNode();
        });

        calendarView.setEntryFactory(param -> {
            DateControl control = param.getDateControl();
            VirtualGrid grid = control.getVirtualGrid();
            ZonedDateTime time = param.getZonedDateTime();
            DayOfWeek firstDayOfWeek = calendarView.getFirstDayOfWeek();

            ZonedDateTime lowerTime = grid.adjustTime(time, false, firstDayOfWeek);
            ZonedDateTime upperTime = grid.adjustTime(time, true, firstDayOfWeek);

            if (Duration.between(time, lowerTime).abs().minus(Duration.between(time, upperTime).abs()).isNegative()) {
                time = lowerTime;
            } else {
                time = upperTime;
            }

            Entry<Tache> entry = new Entry<>("Tâche Personnelle");
            Tache tache = new Tache(
                    null,
                    sc.findByUid(Session.getId()),
                    "Tâche Personnelle",
                    "",
                    "3",
                    "#000000",
                    Timestamp.valueOf(time.toLocalDateTime()),
                    Time.valueOf(LocalTime.ofNanoOfDay(entry.getDuration().toNanos()))
            );
            st.ajouter(tache);
            entry.setUserObject(tache);
            entry.changeStartDate(time.toLocalDate());
            entry.changeStartTime(time.toLocalTime());
            entry.changeEndDate(entry.getStartDate());
            entry.changeEndTime(entry.getStartTime().plusHours(1));

            if (control instanceof AllDayView) {
                entry.setFullDay(true);
            }

            refresh();
            resetStats();
            return null;
        });

        calendarView.setEntryContextMenuCallback(param -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Afficher");
            MenuItem item2 = new MenuItem("Modifier");
            MenuItem item3 = new MenuItem("Supprimer");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        PopOver popOver2 = new PopOver();
                        FXMLLoader loader2 = new FXMLLoader(
                                getClass().getResource(
                                        "../ui/consulttache.fxml"
                                )
                        );
                        AnchorPane p2 = loader2.load();
                        ConsultTacheController controller2 = loader2.getController();
                        popOver2.setContentNode(p2);
                        popOver2.setStyle(".popover > .border  {\n"
                                + "    -fx-border-style: none;\n"
                                + "    -fx-padding: 0;\n"
                                + "    -fx-border-width: 0;\n"
                                + "    -fx-fill: rgba(255,255,255,0.5);\n"
                                + "}");
                        popOver2.setArrowSize(0);
                        controller2.setTache(Integer.parseInt(param.getEntry().getId()));
                        popOver2.show(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            item2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        PopOver popOver2 = new PopOver();
                        FXMLLoader loader2 = new FXMLLoader(
                                getClass().getResource(
                                        "../ui/modiftache.fxml"
                                )
                        );
                        AnchorPane p2 = loader2.load();
                        ModifTacheController controller2 = loader2.getController();
                        popOver2.setContentNode(p2);
                        popOver2.setStyle(".popover > .border  {\n"
                                + "    -fx-border-style: none;\n"
                                + "    -fx-padding: 0;\n"
                                + "    -fx-border-width: 0;\n"
                                + "    -fx-fill: rgba(255,255,255,0.5);\n"
                                + "}");
                        popOver2.setArrowSize(0);
                        controller2.setTache(Integer.parseInt(param.getEntry().getId()));
                        popOver2.show(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            item3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
                    ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(AlertType.WARNING,
                            "Voulez-vous vraiment supprimer la tâche ?",
                            oui,
                            non
                    );
                    alert.setTitle("Suppression de Tâche");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.orElse(non) == oui) {
                        st.supprimer(st.find(Integer.parseInt(param.getEntry().getId())));
                        refresh();
                        resetStats();
                    }
                }
            });
            if (!((doctourna.models.Tache) param.getEntry().getUserObject()).getType().contains("1")) {
                contextMenu.getItems().addAll(item1, item2, item3);
            }
            return contextMenu;
        });

        calendarView.setEntryEditPolicy(param -> {
            if (param.getEditOperation() == DateControl.EditOperation.MOVE) {
                ((Tache) param.getEntry().getUserObject()).setDate(Timestamp.valueOf(param.getEntry().getStartAsLocalDateTime()));
                st.modifier(((Tache) param.getEntry().getUserObject()));
            }
            else if (param.getEditOperation() == DateControl.EditOperation.DELETE)
                return false;
            else {
                ((Tache) param.getEntry().getUserObject()).setDuree(Time.valueOf(LocalTime.ofSecondOfDay(param.getEntry().getDuration().getSeconds())));
                st.modifier(((Tache) param.getEntry().getUserObject()));
            }
            return true;
        });
        
        calendarView.setBorder(new Border(new BorderStroke(Color.CORNFLOWERBLUE, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4d))));

        Scene scene = new Scene(calendarView);
        scene.getStylesheets().add(this.getClass().getResource("calendrier.css").toExternalForm());
        primaryStage.setTitle("Calendrier");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
