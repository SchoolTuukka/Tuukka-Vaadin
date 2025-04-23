package com.tuk.prj.views.masterdetailruns;

import com.tuk.prj.data.Game;
import com.tuk.prj.data.SpeedRuns;
import com.tuk.prj.services.SpeedRunsService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Master-Detail-runs")
@Route("/:speedRunsID?/:action?(edit)")
@Menu(order = 0, icon = LineAwesomeIconUrl.COLUMNS_SOLID)
@RouteAlias("")
@RolesAllowed("ADMIN")
public class MasterDetailrunsView extends Div implements BeforeEnterObserver {

    private final String SPEEDRUNS_ID = "speedRunsID";
    private final String SPEEDRUNS_EDIT_ROUTE_TEMPLATE = "/%s/edit";

    private final Grid<SpeedRuns> grid = new Grid<>(SpeedRuns.class, false);
    private final Binder<SpeedRuns> binder = new BeanValidationBinder<>(SpeedRuns.class); // Remove redundant binder declaration

    private TextField name;
    private TextField lastname;
    private TextField email;
    private TextField game;
    private TextField time;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private SpeedRuns speedRuns;

    private final SpeedRunsService speedRunsService;

    @Autowired
    public MasterDetailrunsView(SpeedRunsService speedRunsService) {
        this.speedRunsService = speedRunsService;
        addClassNames("master-detailruns-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("lastname").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn(speedRun -> {
            Game game = speedRun.getGame();
            return game != null ? game.getTitle() : "";
        }).setHeader("Game").setAutoWidth(true);
        grid.addColumn("time").setAutoWidth(true);
        grid.setItems(query -> speedRunsService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SPEEDRUNS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailrunsView.class);
            }
        });

        // Configure Form
        binder.forField(time).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("time");

        binder.forField(game)
                .withConverter(new Converter<String, Game>() {
                    @Override
                    public Result<Game> convertToModel(String value, ValueContext context) {
                        if (value != null && !value.isEmpty()) {
                            // Find or create the Game object based on the string input (e.g., find by game title)
                            Game game = new Game(); // Example: Replace with actual lookup or creation logic
                            game.setTitle(value);  // Set the title of the game
                            return Result.ok(game);
                        } else {
                            return Result.error("Game title cannot be empty");
                        }
                    }

                    @Override
                    public String convertToPresentation(Game value, ValueContext context) {
                        // Convert the Game object to a String (e.g., the game's title)
                        return value != null ? value.getTitle() : "";
                    }
                })
                .bind(SpeedRuns::getGame, SpeedRuns::setGame);

        binder.bindInstanceFields(this);


        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.speedRuns == null) {
                    this.speedRuns = new SpeedRuns();
                }
                binder.writeBean(this.speedRuns);
                speedRunsService.save(this.speedRuns);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(MasterDetailrunsView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> speedRunsId = event.getRouteParameters().get(SPEEDRUNS_ID).map(Long::parseLong);
        if (speedRunsId.isPresent()) {
            Optional<SpeedRuns> speedRunsFromBackend = speedRunsService.get(speedRunsId.get());
            if (speedRunsFromBackend.isPresent()) {
                populateForm(speedRunsFromBackend.get());
            } else {
                Notification.show(String.format("The requested speedRuns was not found, ID = %s", speedRunsId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailrunsView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        lastname = new TextField("Lastname");
        email = new TextField("Email");
        game = new TextField("Game");
        time = new TextField("Time");
        formLayout.add(name, lastname, email, game, time);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(SpeedRuns value) {
        this.speedRuns = value;
        binder.readBean(this.speedRuns);

                }
}
