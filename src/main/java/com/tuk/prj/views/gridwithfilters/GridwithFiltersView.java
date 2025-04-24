package com.tuk.prj.views.gridwithfilters;

import com.tuk.prj.data.Game;
import com.tuk.prj.data.GameRepository;
import com.tuk.prj.data.SpeedRuns;
import com.tuk.prj.services.SpeedRunsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Speed Runs Grid")
@Route("speedruns-grid")
@Menu(order = 2, icon = LineAwesomeIconUrl.FILTER_SOLID)
@AnonymousAllowed
@Uses(Icon.class)

public class GridwithFiltersView extends Div {

    private Grid<SpeedRuns> grid;
    private Filters filters;
    private final SpeedRunsService speedRunService;
    private final GameRepository gameRepository;

    public GridwithFiltersView(SpeedRunsService speedRunService, GameRepository gameRepository) {
        this.speedRunService = speedRunService;
        this.gameRepository = gameRepository;
        setSizeFull();
        addClassNames("gridwith-filters-view");


        filters = new Filters(this::refreshGrid,gameRepository);
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER, "mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<SpeedRuns> {

        private final TextField name = new TextField("Name");
        private final TextField lastname = new TextField("Last Name");
        private final TextField email = new TextField("Email");
        private final ComboBox<String> games = new ComboBox<>("Game");

        public Filters(Runnable onSearch, GameRepository gameRepository) {
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            // Fetch game titles from the DB and set them
            List<String> gameTitles = gameRepository.findAll().stream()
                    .map(Game::getTitle)
                    .collect(Collectors.toList());
            games.setItems(gameTitles);

            Button resetBtn = new Button("Reset", e -> {
                name.clear();
                lastname.clear();
                email.clear();
                games.clear();
                onSearch.run();
            });
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClassName("special");

            Button searchBtn = new Button("Search", e -> onSearch.run());
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name, lastname, email, games, actions);
        }

        @Override
        public Predicate toPredicate(Root<SpeedRuns> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();

            // Apply filter for name
            if (!name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.getValue().toLowerCase() + "%"));
            }

            // Apply filter for lastname
            if (!lastname.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + lastname.getValue().toLowerCase() + "%"));
            }

            // Apply filter for email
            if (!email.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.getValue().toLowerCase() + "%"));
            }

            // Apply filter for game
            if (games.getValue() != null && !games.getValue().isEmpty()) {
                predicates.add(cb.equal(root.get("game").get("title"), games.getValue()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }
    }


    private Component createGrid() {
        grid = new Grid<>(SpeedRuns.class, false);

// Include ID
        grid.addColumn(SpeedRuns::getId).setHeader("ID").setAutoWidth(true);

// Other columns
        grid.addColumn(SpeedRuns::getName).setHeader("Name").setAutoWidth(true);
        grid.addColumn(SpeedRuns::getLastname).setHeader("Last Name").setAutoWidth(true);
        grid.addColumn(SpeedRuns::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(speedRun -> {
            Game game = speedRun.getGame();
            return game != null ? game.getTitle() : "";
        }).setHeader("Game").setAutoWidth(true);
        grid.addColumn(SpeedRuns::getTime).setHeader("Time (s)").setAutoWidth(true);

        grid.setItems(query -> speedRunService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), filters)
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}